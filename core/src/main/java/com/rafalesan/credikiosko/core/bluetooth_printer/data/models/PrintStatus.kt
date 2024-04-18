package com.rafalesan.credikiosko.core.bluetooth_printer.data.models

sealed class PrintStatus {

    data object ConnectingPrinter: PrintStatus()
    data object PrinterConnected: PrintStatus()
    data object Printing: PrintStatus()
    data object PrintSuccess: PrintStatus()
    class PrinterConnectionError(val exception: Exception): PrintStatus()

}
