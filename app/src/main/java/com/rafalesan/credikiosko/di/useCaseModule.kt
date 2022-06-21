package com.rafalesan.credikiosko.di

import com.rafalesan.credikiosko.domain.account.usecases.ChangeThemeUseCase
import com.rafalesan.credikiosko.domain.account.usecases.GetThemeUseCase
import com.rafalesan.credikiosko.domain.account.usecases.SaveUserSessionUseCase
import com.rafalesan.credikiosko.domain.auth.usecases.ExistUserSession
import com.rafalesan.credikiosko.domain.auth.usecases.LoginUseCase
import com.rafalesan.credikiosko.domain.auth.usecases.SignupUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { LoginUseCase(get(), get()) }
    single { SignupUseCase(get(), get()) }
    single { SaveUserSessionUseCase(get()) }
    single { GetThemeUseCase(get()) }
    single { ChangeThemeUseCase(get()) }
    single { ExistUserSession(get()) }
}
