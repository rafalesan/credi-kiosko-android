package com.rafalesan.credikiosko.core.commons.domain.usecases

import com.rafalesan.credikiosko.core.commons.domain.repository.ISessionRepository
import javax.inject.Inject

class ChangeThemeUseCase @Inject constructor(
    private val accountRepository: ISessionRepository
) {

    suspend operator fun invoke(lightTheme: Boolean) {
        accountRepository.changeTheme(lightTheme)
    }

}
