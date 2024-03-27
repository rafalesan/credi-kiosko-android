package com.rafalesan.credikiosko.credits.presentation.credits_list

import com.rafalesan.credikiosko.credits.domain.entity.Credit

sealed class CreditsAction {

    data object ShowCreditForm : CreditsAction()
    class ShowCreditViewer(val credit: Credit) : CreditsAction()

}
