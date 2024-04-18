package com.rafalesan.credikiosko.core.bluetooth_printer.domain.repository

import com.rafalesan.credikiosko.core.bluetooth_printer.domain.entity.BluetoothDevice

interface IBluetoothPrinterRepository {

    suspend fun savePrinter(bluetoothDevice: BluetoothDevice)
    suspend fun existsPrinterConfigured(): Boolean
    suspend fun removePrinterAlreadyConfigured()
}
