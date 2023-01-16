package com.rafalesan.credikiosko.auth.login

sealed class LoginEvent {
    object OpenSignup : LoginEvent()
    object OpenHome: LoginEvent()
}
