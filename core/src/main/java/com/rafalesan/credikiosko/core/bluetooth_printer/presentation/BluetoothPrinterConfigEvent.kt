package com.rafalesan.credikiosko.core.bluetooth_printer.presentation

import android.bluetooth.BluetoothDevice
import com.rafalesan.credikiosko.core.bluetooth_printer.presentation.model.BluetoothDeviceInfo

sealed class BluetoothPrinterConfigEvent {
    class SetBondedBluetoothDevices(val devices: List<BluetoothDevice>): BluetoothPrinterConfigEvent()
    class BluetoothDevicePressed(val bluetoothDeviceInfo: BluetoothDeviceInfo): BluetoothPrinterConfigEvent()
}
