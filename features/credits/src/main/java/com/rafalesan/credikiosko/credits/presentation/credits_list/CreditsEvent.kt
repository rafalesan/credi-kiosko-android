package com.rafalesan.credikiosko.credits.presentation.credits_list

import com.rafalesan.credikiosko.credits.domain.entity.Credit

sealed class CreditsEvent {
    data object CreateNewCredit: CreditsEvent()
    class ShowCredit(val credit: Credit): CreditsEvent()
}
