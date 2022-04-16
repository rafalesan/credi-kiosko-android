package com.rafalesan.credikiosko.domain.auth.repository

import com.rafalesan.credikiosko.domain.auth.entity.UserSession
import com.rafalesan.credikiosko.domain.auth.usecases.LoginUseCase
import com.rafalesan.credikiosko.domain.utils.Result

interface IAuthRepository {

    suspend fun login(credentials: LoginUseCase.Credentials): Result<UserSession>

}
