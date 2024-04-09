package com.rafalesan.credikiosko.core.bluetooth_printer.domain.repository

import com.rafalesan.credikiosko.core.bluetooth_printer.domain.entity.BluetoothPrinter

interface IBluetoothPrinterRepository {

    suspend fun savePrinter(printer: BluetoothPrinter)
    suspend fun existsPrinterConfigured(): Boolean

}
