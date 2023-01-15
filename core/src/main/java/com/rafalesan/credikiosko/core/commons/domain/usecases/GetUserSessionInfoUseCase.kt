package com.rafalesan.credikiosko.core.commons.domain.usecases

import com.rafalesan.credikiosko.core.commons.domain.repository.IAccountRepository

class GetUserSessionInfoUseCase(private val accountRepository: IAccountRepository) {

    operator fun invoke() = accountRepository.getUserSessionInfo()

}
