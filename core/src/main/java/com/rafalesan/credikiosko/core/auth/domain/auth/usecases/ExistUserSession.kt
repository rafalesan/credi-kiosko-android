package com.rafalesan.credikiosko.core.auth.domain.auth.usecases

import com.rafalesan.credikiosko.core.auth.domain.auth.repository.IAuthRepository
import javax.inject.Inject

class ExistUserSession @Inject constructor(
    private val authRepository: IAuthRepository
) {

    operator fun invoke() = authRepository.existUserSession()

}
