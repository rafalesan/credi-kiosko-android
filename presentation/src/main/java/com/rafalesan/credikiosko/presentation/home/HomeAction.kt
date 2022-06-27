package com.rafalesan.credikiosko.presentation.home

sealed class HomeAction {
    class HomeOptionSelected(val homeOption: HomeOption): HomeAction()
}
