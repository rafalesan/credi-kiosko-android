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
import com.rafalesan.credikiosko.domain.utils.Result

class AuthRepository(private val authDataSource: AuthDataSource) : IAuthRepository {

    override suspend fun login(credentials: LoginUseCase.Credentials): Result<UserSession, Nothing> {

        val loginRequest = credentials.toLoginRequest()

        return when(val apiResult = authDataSource.login(loginRequest)) {
            is ApiResult.Success -> {
                val userSession = apiResult.response.data.toUserSession()
                Result.Success(userSession)
            }
            is ApiResult.Error   -> {
                when(apiResult.exception) {
                    is NoInternetException -> Result.Failure.NoInternet
                    is ApiException        -> Result.Failure.ApiFailure(apiResult.exception.message,
                                                                        apiResult.exception.errors)
                    else -> Result.Failure.UnknownFailure
                }
            }
        }

    }

}
