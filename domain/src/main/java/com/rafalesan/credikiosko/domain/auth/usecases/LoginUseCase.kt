package com.rafalesan.credikiosko.domain.auth.usecases

import com.rafalesan.credikiosko.domain.auth.entity.UserSession
import com.rafalesan.credikiosko.domain.auth.repository.IAuthRepository
import com.rafalesan.credikiosko.domain.utils.Result

class LoginUseCase(private val authRepository: IAuthRepository) {

    suspend operator fun invoke(credentials: Credentials): Result<UserSession> {
        val result = authRepository.login(credentials)
        return result
    }

    data class Credentials(val email: String?,
                           val password: String?,
                           val deviceName: String?)

}
