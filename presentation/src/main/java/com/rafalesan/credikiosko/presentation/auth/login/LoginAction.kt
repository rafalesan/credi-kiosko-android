package com.rafalesan.credikiosko.presentation.auth.login

sealed class LoginAction {
    object Login: LoginAction()
    object CreateAccount: LoginAction()
}
