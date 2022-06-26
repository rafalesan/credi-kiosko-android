package com.rafalesan.credikiosko.presentation.main

sealed class MainAction {
    class ChangeTheme(val lightTheme: Boolean): MainAction()
}
