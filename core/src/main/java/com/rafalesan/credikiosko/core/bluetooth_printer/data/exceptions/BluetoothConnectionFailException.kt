package com.rafalesan.credikiosko.core.bluetooth_printer.data.exceptions

class BluetoothConnectionFailException(
    val bluetoothDeviceName: String?,
    message: String = "Unable to connect to $bluetoothDeviceName"
) : Exception(message)
