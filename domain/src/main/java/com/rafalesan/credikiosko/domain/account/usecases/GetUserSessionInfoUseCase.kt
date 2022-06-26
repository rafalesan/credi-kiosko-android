package com.rafalesan.credikiosko.domain.account.usecases

import com.rafalesan.credikiosko.domain.account.repository.IAccountRepository

class GetUserSessionInfoUseCase(private val accountRepository: IAccountRepository) {

    operator fun invoke() = accountRepository.getUserSessionInfo()

}
