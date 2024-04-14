package com.rafalesan.credikiosko.core.bluetooth_printer.presentation

import com.rafalesan.credikiosko.core.bluetooth_printer.domain.entity.BluetoothDevice

data class BluetoothPrinterConfigViewState(
    val bondedBluetoothDevices: List<BluetoothDevice> = emptyList(),
    val isPrinterConfigured: Boolean = false,
    val showAllBluetoothDevices: Boolean = false,
)
