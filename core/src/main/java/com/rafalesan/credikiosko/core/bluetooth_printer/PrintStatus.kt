package com.rafalesan.credikiosko.core.bluetooth_printer

sealed class PrintStatus {

    data object ConnectingPrinter: PrintStatus()
    data object PrinterConnected: PrintStatus()
    data object Printing: PrintStatus()
    data object PrintSuccess: PrintStatus()
    class PrinterConnectionError(val errorDescription: String): PrintStatus()

}
