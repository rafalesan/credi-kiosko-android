package com.rafalesan.credikiosko.core.bluetooth_printer.presentation

import com.rafalesan.credikiosko.core.bluetooth_printer.presentation.model.BluetoothDeviceInfo

data class BluetoothPrinterConfigViewState(
    val bondedBluetoothDevices: List<BluetoothDeviceInfo> = emptyList(),
    val isPrinterConfigured: Boolean = false
)
