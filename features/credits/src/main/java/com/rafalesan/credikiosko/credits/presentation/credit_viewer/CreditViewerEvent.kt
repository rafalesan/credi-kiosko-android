package com.rafalesan.credikiosko.credits.presentation.credit_viewer

sealed class CreditViewerEvent {
    class PrintCredit(val checkBluetoothAvailability: Boolean = true) : CreditViewerEvent()
    data object CancelPrintingRetry : CreditViewerEvent()
    data object RetryPrinting : CreditViewerEvent()
    data object CancelPrinterConfiguration : CreditViewerEvent()
    data object StartPrinterConfiguration : CreditViewerEvent()
}
