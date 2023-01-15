package com.rafalesan.credikiosko.di

import com.rafalesan.credikiosko.core.auth.domain.auth.usecases.ExistUserSession
import com.rafalesan.credikiosko.core.auth.domain.auth.usecases.LoginUseCase
import com.rafalesan.credikiosko.core.auth.domain.auth.usecases.SignupUseCase
import com.rafalesan.credikiosko.core.commons.domain.usecases.ChangeThemeUseCase
import com.rafalesan.credikiosko.core.commons.domain.usecases.GetThemeUseCase
import com.rafalesan.credikiosko.core.commons.domain.usecases.GetUserSessionInfoUseCase
import com.rafalesan.credikiosko.core.commons.domain.usecases.SaveUserSessionUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { LoginUseCase(get(), get()) }
    single { SignupUseCase(get(), get()) }
    single { SaveUserSessionUseCase(get()) }
    single { GetThemeUseCase(get()) }
    single { ChangeThemeUseCase(get()) }
    single { ExistUserSession(get()) }
    single { GetUserSessionInfoUseCase(get()) }
}
