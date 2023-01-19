package com.rafalesan.credikiosko.core.auth.domain.auth.repository

import com.rafalesan.credikiosko.core.auth.domain.auth.usecases.LoginUseCase
import com.rafalesan.credikiosko.core.auth.domain.auth.usecases.SignupUseCase
import com.rafalesan.credikiosko.core.commons.domain.entity.UserSession
import com.rafalesan.credikiosko.core.commons.domain.utils.ResultOf

interface IAuthRepository {

    suspend fun login(credentials: LoginUseCase.Credentials): ResultOf<UserSession, Nothing>
    suspend fun signup(signupData: SignupUseCase.SignupData): ResultOf<UserSession, Nothing>

}
