package com.rafalesan.credikiosko.auth.login

sealed class LoginAction {
    object OpenSignup : LoginAction()
    object OpenHome: LoginAction()
}
