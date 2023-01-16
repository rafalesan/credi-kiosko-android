package com.rafalesan.credikiosko.di

import com.rafalesan.credikiosko.auth.AuthViewModel
import com.rafalesan.credikiosko.auth.login.LoginViewModel
import com.rafalesan.credikiosko.auth.signup.SignupViewModel
import com.rafalesan.credikiosko.presentation.home.HomeViewModel
import com.rafalesan.credikiosko.presentation.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel { LoginViewModel(get()) }
    viewModel { SignupViewModel(get()) }
    viewModel { AuthViewModel(get(), get(), get()) }
    viewModel { MainViewModel(get(), get(), get()) }
    viewModel { HomeViewModel() }

}