package com.rafalesan.credikiosko.core.auth.domain.auth.usecases

import com.rafalesan.credikiosko.core.auth.domain.auth.repository.IAuthRepository

class ExistUserSession(private val authRepository: IAuthRepository) {

    operator fun invoke() = authRepository.existUserSession()

}
