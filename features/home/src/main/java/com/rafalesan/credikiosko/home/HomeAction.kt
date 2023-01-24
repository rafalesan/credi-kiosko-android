package com.rafalesan.credikiosko.home

sealed class HomeAction {
    class HomeOptionSelected(val homeOption: HomeOption): HomeAction()
}
