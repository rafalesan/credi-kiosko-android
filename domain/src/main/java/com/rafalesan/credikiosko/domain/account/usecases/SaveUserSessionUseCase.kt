package com.rafalesan.credikiosko.domain.account.usecases

import com.rafalesan.credikiosko.domain.account.repository.IAccountRepository
import com.rafalesan.credikiosko.domain.auth.entity.UserSession

class SaveUserSessionUseCase(private val accountRepository: IAccountRepository) {

    suspend operator fun invoke(userSession: UserSession) = accountRepository.saveUserSession(userSession)

}
