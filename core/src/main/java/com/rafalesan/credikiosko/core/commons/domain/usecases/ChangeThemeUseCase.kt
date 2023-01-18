package com.rafalesan.credikiosko.core.commons.domain.usecases

import com.rafalesan.credikiosko.core.commons.domain.repository.IAccountRepository
import javax.inject.Inject

class ChangeThemeUseCase @Inject constructor(
    private val accountRepository: IAccountRepository
) {

    suspend operator fun invoke(lightTheme: Boolean) {
        accountRepository.changeTheme(lightTheme)
    }

}
