package com.rafalesan.credikiosko.domain.account.usecases

import com.rafalesan.credikiosko.domain.account.repository.IAccountRepository

class ChangeThemeUseCase(private val accountRepository: IAccountRepository) {

    suspend operator fun invoke(lightTheme: Boolean) {
        accountRepository.changeTheme(lightTheme)
    }

}
