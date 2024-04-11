package com.rafalesan.credikiosko.core.bluetooth_printer.domain.entity

data class BluetoothPrinter(
    val id: Long = 0,
    val name: String,
    val macAddress: String
)