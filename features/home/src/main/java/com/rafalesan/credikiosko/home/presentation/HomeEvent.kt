package com.rafalesan.credikiosko.home.presentation

sealed class HomeEvent {
    class HomeOptionSelected(val homeOption: HomeOption): HomeEvent()
}
