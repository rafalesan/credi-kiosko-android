package com.rafalesan.credikiosko.di

import com.rafalesan.credikiosko.presentation.auth.AuthViewModel
import com.rafalesan.credikiosko.presentation.auth.login.LoginViewModel
import com.rafalesan.credikiosko.presentation.auth.signup.SignupViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel { LoginViewModel(get()) }
    viewModel { SignupViewModel(get()) }
    viewModel { AuthViewModel(get(), get()) }

}