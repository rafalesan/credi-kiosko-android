package com.rafalesan.credikiosko.core.commons.domain.usecases

import com.rafalesan.credikiosko.core.commons.domain.entity.UserSession
import com.rafalesan.credikiosko.core.commons.domain.repository.ISessionRepository
import javax.inject.Inject

class SaveUserSessionUseCase @Inject constructor (
    private val accountRepository: ISessionRepository
) {

    suspend operator fun invoke(userSession: UserSession) = accountRepository.saveUserSession(userSession)

}
