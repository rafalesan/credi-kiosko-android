package com.rafalesan.credikiosko.core.commons.domain.usecases

import com.rafalesan.credikiosko.core.commons.domain.repository.ISessionRepository
import javax.inject.Inject

class GetThemeUseCase @Inject constructor(
    private val accountRepository: ISessionRepository
) {

    operator fun invoke() = accountRepository.getTheme()

}
