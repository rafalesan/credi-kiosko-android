package com.rafalesan.credikiosko.di

import com.rafalesan.credikiosko.presentation.auth.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel { LoginViewModel(get()) }

}