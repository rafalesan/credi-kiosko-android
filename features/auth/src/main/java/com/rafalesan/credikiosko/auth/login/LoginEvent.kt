package com.rafalesan.credikiosko.auth.login

sealed class LoginEvent {
    object Login: LoginEvent()
    object CreateAccount: LoginEvent()
}
