package com.rafalesan.credikiosko.core.commons.domain.usecases

import com.rafalesan.credikiosko.core.commons.domain.entity.UserSession
import com.rafalesan.credikiosko.core.commons.domain.repository.IAccountRepository
import javax.inject.Inject

class SaveUserSessionUseCase @Inject constructor (
    private val accountRepository: IAccountRepository
) {

    suspend operator fun invoke(userSession: UserSession) = accountRepository.saveUserSession(userSession)

}
