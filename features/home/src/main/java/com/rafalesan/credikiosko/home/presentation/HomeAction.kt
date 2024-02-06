package com.rafalesan.credikiosko.home.presentation

sealed class HomeAction {
    class NavigateTo(val route: String): HomeAction()
}
