package com.rafalesan.credikiosko.data.auth.repository

import com.rafalesan.credikiosko.data.auth.datasource.AuthDataSource
import com.rafalesan.credikiosko.data.auth.mappers.toLoginRequest
import com.rafalesan.credikiosko.data.auth.mappers.toUserSession
import com.rafalesan.credikiosko.data.auth.utils.ApiException
import com.rafalesan.credikiosko.data.auth.utils.ApiResult
import com.rafalesan.credikiosko.data.auth.utils.NoInternetException
import com.rafalesan.credikiosko.domain.auth.entity.UserSession
import com.rafalesan.credikiosko.domain.auth.repository.IAuthRepository
import com.rafalesan.credikiosko.domain.auth.usecases.LoginUseCase
import com.rafalesan.credikiosko.domain.utils.FailureStatus
import com.rafalesan.credikiosko.domain.utils.Result

class AuthRepository(private val authDataSource: AuthDataSource) : IAuthRepository {

    override suspend fun login(credentials: LoginUseCase.Credentials): Result<UserSession> {

        val loginRequest = credentials.toLoginRequest()

        return when(val apiResult = authDataSource.login(loginRequest)) {
            is ApiResult.Success -> {
                val userSession = apiResult.response.data.toUserSession()
                Result.Success(userSession)
            }
            is ApiResult.Error   -> {
                when(apiResult.exception) {
                    is NoInternetException -> Result.Failure(FailureStatus.NO_INTERNET)
                    is ApiException        -> Result.Failure(FailureStatus.API_FAILURE,
                                                      apiResult.exception.statusCode,
                                                      apiResult.exception.message)
                    else -> Result.Failure(FailureStatus.UNKNOWN)
                }
            }
        }

    }

}
