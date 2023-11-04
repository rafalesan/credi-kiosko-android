package com.rafalesan.credikiosko.core.auth.data.repository

import com.rafalesan.credikiosko.core.auth.data.mappers.toLoginRequest
import com.rafalesan.credikiosko.core.auth.data.mappers.toSignupRequest
import com.rafalesan.credikiosko.core.auth.data.mappers.toUserSession
import com.rafalesan.credikiosko.core.auth.domain.auth.repository.IAuthRepository
import com.rafalesan.credikiosko.core.auth.domain.auth.usecases.LoginUseCase
import com.rafalesan.credikiosko.core.auth.domain.auth.usecases.SignupUseCase
import com.rafalesan.credikiosko.core.commons.data.utils.ApiResultHandler
import com.rafalesan.credikiosko.core.commons.domain.entity.UserSession
import com.rafalesan.credikiosko.core.commons.domain.utils.ResultOf
import com.rafalesan.credikiosko.data.auth.datasource.remote.AuthDataSource

class AuthRepository(
    private val authDataSource: AuthDataSource
) : IAuthRepository {

    override suspend fun login(credentials: LoginUseCase.Credentials): ResultOf<UserSession, Nothing> {

        val loginRequest = credentials.toLoginRequest()

        val apiResult = authDataSource.login(loginRequest)

        return ApiResultHandler.handle(apiResult) { response ->
            val userSession = response.data.toUserSession()
            ResultOf.Success(userSession)
        }

    }

    override suspend fun signup(signupData: SignupUseCase.SignupData): ResultOf<UserSession, Nothing> {
        val signupRequest = signupData.toSignupRequest()
        val apiResult = authDataSource.signup(signupRequest)

        return ApiResultHandler.handle(apiResult) { response ->
            val userSession = response.data.toUserSession()
            ResultOf.Success(userSession)
        }

    }

}
