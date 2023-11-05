package com.rafalesan.credikiosko.home

sealed class HomeAction {
    class NavigateTo(val route: String): HomeAction()
}
