package com.rafalesan.credikiosko.data.auth.repository

import com.rafalesan.credikiosko.data.auth.datasource.local.UserSessionDataSource
import com.rafalesan.credikiosko.data.auth.datasource.remote.AuthDataSource
import com.rafalesan.credikiosko.data.auth.mappers.toLoginRequest
import com.rafalesan.credikiosko.data.auth.mappers.toSignupRequest
import com.rafalesan.credikiosko.data.auth.mappers.toUserSession
import com.rafalesan.credikiosko.data.utils.ApiResultHandler
import com.rafalesan.credikiosko.domain.auth.entity.UserSession
import com.rafalesan.credikiosko.domain.auth.repository.IAuthRepository
import com.rafalesan.credikiosko.domain.auth.usecases.LoginUseCase
import com.rafalesan.credikiosko.domain.auth.usecases.SignupUseCase
import com.rafalesan.credikiosko.domain.utils.ResultOf
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class AuthRepository(private val authDataSource: AuthDataSource,
                     private val userSessionDataSource: UserSessionDataSource) : IAuthRepository {

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

    override fun existUserSession(): Boolean {
        val userSession = runBlocking { userSessionDataSource.userSession().first() }
        return userSession != null
    }

}
