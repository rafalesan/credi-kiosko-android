package com.rafalesan.credikiosko.auth.login

sealed class LoginAction {
    object Login: LoginAction()
    object CreateAccount: LoginAction()
}
