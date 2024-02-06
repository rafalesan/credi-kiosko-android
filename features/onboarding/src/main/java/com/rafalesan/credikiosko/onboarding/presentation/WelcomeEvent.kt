package com.rafalesan.credikiosko.onboarding.presentation

sealed class WelcomeEvent {

    class SetBusinessName(val businessName: String): WelcomeEvent()
    data object CheckIfBusinessExists: WelcomeEvent()
    data object ConfirmBusinessName: WelcomeEvent()

}
