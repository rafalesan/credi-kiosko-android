package com.rafalesan.credikiosko.home.presentation

sealed class HomeEvent {
    class HomeOptionSelected(val homeOption: HomeOption): HomeEvent()
    data object EditBusinessName: HomeEvent()
    class BusinessNameInputChanged(val businessName: String): HomeEvent()
    data object SubmitBusinessName: HomeEvent()
    data object CancelBusinessNameEdit: HomeEvent()
}
