package com.rafalesan.credikiosko.auth

sealed class AuthEvent {
    object OpenHome: AuthEvent()
}
