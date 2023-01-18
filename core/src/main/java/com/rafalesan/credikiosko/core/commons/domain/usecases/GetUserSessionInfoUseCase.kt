package com.rafalesan.credikiosko.core.commons.domain.usecases

import com.rafalesan.credikiosko.core.commons.domain.repository.IAccountRepository
import javax.inject.Inject

class GetUserSessionInfoUseCase @Inject constructor(
    private val accountRepository: IAccountRepository
) {

    operator fun invoke() = accountRepository.getUserSessionInfo()

}
