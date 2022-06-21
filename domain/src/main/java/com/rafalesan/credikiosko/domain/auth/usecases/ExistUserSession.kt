package com.rafalesan.credikiosko.domain.auth.usecases

import com.rafalesan.credikiosko.domain.auth.repository.IAuthRepository

class ExistUserSession(private val authRepository: IAuthRepository) {

    operator fun invoke() = authRepository.existUserSession()

}
