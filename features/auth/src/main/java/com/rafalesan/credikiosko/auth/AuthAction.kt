package com.rafalesan.credikiosko.auth

sealed class AuthAction {
    class ChangeTheme(val lightTheme: Boolean): AuthAction()
    object SessionValidation: AuthAction()
}
