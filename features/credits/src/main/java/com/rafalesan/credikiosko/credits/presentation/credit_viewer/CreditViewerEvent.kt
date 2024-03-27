package com.rafalesan.credikiosko.credits.presentation.credit_viewer

sealed class CreditViewerEvent {
    data object PrintCredit : CreditViewerEvent()
}
