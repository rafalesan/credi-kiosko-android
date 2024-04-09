package com.rafalesan.credikiosko.core.bluetooth_printer.data.datasource

import com.rafalesan.credikiosko.core.room.dao.BluetoothPrinterDao
import com.rafalesan.credikiosko.core.room.entity.BluetoothPrinterEntity
import javax.inject.Inject

class BluetoothPrinterLocalDataSource @Inject constructor(
    private val bluetoothPrinterDao: BluetoothPrinterDao
) {

    suspend fun existsPrinter(): Boolean {
        return bluetoothPrinterDao.existsPrinter()
    }

    suspend fun insertPrinter(bluetoothPrinterEntity: BluetoothPrinterEntity) {
        bluetoothPrinterDao.insertPrinter(bluetoothPrinterEntity)
    }

    suspend fun getBluetoothPrinterConfigured(): BluetoothPrinterEntity? {
        return bluetoothPrinterDao.getBluetoothPrinter()
    }

}
