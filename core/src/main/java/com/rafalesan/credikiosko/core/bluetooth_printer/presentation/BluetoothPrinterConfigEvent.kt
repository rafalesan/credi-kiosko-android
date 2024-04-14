package com.rafalesan.credikiosko.core.bluetooth_printer.presentation

import com.rafalesan.credikiosko.core.bluetooth_printer.domain.entity.BluetoothDevice

sealed class BluetoothPrinterConfigEvent {
    data object RefreshBluetoothDevices : BluetoothPrinterConfigEvent()
    class BluetoothDevicePressed(val bluetoothDevice: BluetoothDevice): BluetoothPrinterConfigEvent()
    class ShowAllDevicesChanged(val showAllBluetoothDevices: Boolean): BluetoothPrinterConfigEvent()
}
