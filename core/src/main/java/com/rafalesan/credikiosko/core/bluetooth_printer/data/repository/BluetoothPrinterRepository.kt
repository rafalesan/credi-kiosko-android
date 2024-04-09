package com.rafalesan.credikiosko.core.bluetooth_printer.data.repository

import com.rafalesan.credikiosko.core.bluetooth_printer.data.datasource.BluetoothPrinterLocalDataSource
import com.rafalesan.credikiosko.core.bluetooth_printer.data.mappers.toBluetoothPrinterEntity
import com.rafalesan.credikiosko.core.bluetooth_printer.domain.entity.BluetoothPrinter
import com.rafalesan.credikiosko.core.bluetooth_printer.domain.repository.IBluetoothPrinterRepository


class BluetoothPrinterRepository(
    private val bluetoothPrinterLocalDataSource: BluetoothPrinterLocalDataSource
) : IBluetoothPrinterRepository {

    override suspend fun savePrinter(printer: BluetoothPrinter) {
        bluetoothPrinterLocalDataSource.insertPrinter(
            printer.toBluetoothPrinterEntity()
        )
    }

    override suspend fun existsPrinterConfigured(): Boolean {
        return bluetoothPrinterLocalDataSource.existsPrinter()
    }

}
