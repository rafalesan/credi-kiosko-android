package com.rafalesan.credikiosko.presentation.auth

sealed class AuthEvent {
    object OpenHome: AuthEvent()
}
