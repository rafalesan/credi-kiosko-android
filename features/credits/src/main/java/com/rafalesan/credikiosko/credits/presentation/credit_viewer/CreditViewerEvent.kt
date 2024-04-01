package com.rafalesan.credikiosko.credits.presentation.credit_viewer

sealed class CreditViewerEvent {
    data object PrintCredit : CreditViewerEvent()
    data object CancelPrintingRetry : CreditViewerEvent()
    data object RetryPrinting : CreditViewerEvent()
}
