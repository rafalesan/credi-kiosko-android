package com.rafalesan.credikiosko.core.commons.domain.usecases

import com.rafalesan.credikiosko.core.commons.domain.entity.UserSession
import com.rafalesan.credikiosko.core.commons.domain.repository.IAccountRepository

class SaveUserSessionUseCase(private val accountRepository: IAccountRepository) {

    suspend operator fun invoke(userSession: UserSession) = accountRepository.saveUserSession(userSession)

}
