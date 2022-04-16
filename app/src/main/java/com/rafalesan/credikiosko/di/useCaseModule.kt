package com.rafalesan.credikiosko.di

import com.rafalesan.credikiosko.domain.auth.usecases.LoginUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { LoginUseCase(get()) }
}