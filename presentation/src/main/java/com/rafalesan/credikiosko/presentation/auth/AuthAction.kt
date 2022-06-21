package com.rafalesan.credikiosko.presentation.auth

sealed class AuthAction {
    class ChangeTheme(val lightTheme: Boolean): AuthAction()
    object SessionValidation: AuthAction()
}
