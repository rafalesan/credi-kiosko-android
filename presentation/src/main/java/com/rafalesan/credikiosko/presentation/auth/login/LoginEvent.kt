package com.rafalesan.credikiosko.presentation.auth.login

sealed class LoginEvent {
    object OpenSignup : LoginEvent()
}
