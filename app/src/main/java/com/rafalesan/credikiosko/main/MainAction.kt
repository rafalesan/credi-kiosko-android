package com.rafalesan.credikiosko.main

sealed class MainAction {
    class ChangeTheme(val lightTheme: Boolean): MainAction()
}
