package com.rafalesan.credikiosko.core.bluetooth_printer.data.mappers

import android.annotation.SuppressLint
import com.rafalesan.credikiosko.core.bluetooth_printer.domain.entity.BluetoothDevice
import com.rafalesan.credikiosko.core.room.entity.BluetoothPrinterEntity
import android.bluetooth.BluetoothDevice as AndroidBluetoothDevice

@SuppressLint("MissingPermission")
fun AndroidBluetoothDevice.toBluetoothDeviceDomain(): BluetoothDevice {
    return BluetoothDevice(
        name = name,
        macAddress = address
    )
}

fun BluetoothDevice.toBluetoothPrinterEntity(): BluetoothPrinterEntity {
    return BluetoothPrinterEntity(
        name = name,
        macAddress = macAddress
    )
}