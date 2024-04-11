package com.rafalesan.credikiosko.core.bluetooth_printer.presentation.mappers

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import com.rafalesan.credikiosko.core.bluetooth_printer.domain.entity.BluetoothPrinter
import com.rafalesan.credikiosko.core.bluetooth_printer.presentation.model.BluetoothDeviceInfo

@SuppressLint("MissingPermission")
fun BluetoothDevice.toBluetoothDeviceInfo() : BluetoothDeviceInfo {
    return BluetoothDeviceInfo(
        name = name,
        macAddress = address
    )
}

fun BluetoothDeviceInfo.toBluetoothPrinter() : BluetoothPrinter {
    return BluetoothPrinter(
        name = name,
        macAddress = macAddress
    )
}