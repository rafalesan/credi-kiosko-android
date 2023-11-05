package com.rafalesan.credikiosko.home

sealed class HomeEvent {
    class HomeOptionSelected(val homeOption: HomeOption): HomeEvent()
}
