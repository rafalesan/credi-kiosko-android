package com.rafalesan.credikiosko.core.bluetooth_printer.presentation

import com.rafalesan.credikiosko.core.bluetooth_printer.domain.entity.BluetoothDevice

sealed class BluetoothPrinterConfigEvent {
    class BluetoothDevicePressed(val bluetoothDevice: BluetoothDevice): BluetoothPrinterConfigEvent()
}