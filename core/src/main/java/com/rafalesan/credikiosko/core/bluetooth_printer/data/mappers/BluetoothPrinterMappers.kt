package com.rafalesan.credikiosko.core.bluetooth_printer.data.mappers

import com.rafalesan.credikiosko.core.bluetooth_printer.domain.entity.BluetoothPrinter
import com.rafalesan.credikiosko.core.room.entity.BluetoothPrinterEntity

fun BluetoothPrinter.toBluetoothPrinterEntity(): BluetoothPrinterEntity {
    return BluetoothPrinterEntity(
        id,
        name,
        macAddress
    )
}
