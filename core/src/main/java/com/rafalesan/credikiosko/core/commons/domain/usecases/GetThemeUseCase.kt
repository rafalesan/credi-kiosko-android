package com.rafalesan.credikiosko.core.commons.domain.usecases

import com.rafalesan.credikiosko.core.commons.domain.repository.IAccountRepository
import javax.inject.Inject

class GetThemeUseCase @Inject constructor(
    private val accountRepository: IAccountRepository
) {

    operator fun invoke() = accountRepository.getTheme()

}
