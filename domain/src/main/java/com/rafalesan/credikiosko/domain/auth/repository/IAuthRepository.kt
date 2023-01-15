package com.rafalesan.credikiosko.domain.auth.repository

import com.rafalesan.credikiosko.core.commons.domain.entity.UserSession
import com.rafalesan.credikiosko.core.commons.domain.utils.ResultOf
import com.rafalesan.credikiosko.domain.auth.usecases.LoginUseCase
import com.rafalesan.credikiosko.domain.auth.usecases.SignupUseCase

interface IAuthRepository {

    suspend fun login(credentials: LoginUseCase.Credentials): ResultOf<UserSession, Nothing>
    suspend fun signup(signupData: SignupUseCase.SignupData): ResultOf<UserSession, Nothing>
    fun existUserSession(): Boolean

}
