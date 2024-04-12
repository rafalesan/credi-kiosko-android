package com.rafalesan.credikiosko.core.bluetooth_printer.domain.entity

data class BluetoothDevice(
    val name: String,
    val macAddress: String,
    val type: DeviceType = DeviceType.Unknown
)

enum class DeviceType {
    Unknown,
    Printer,
    Keyboard,
    Mouse,
    Controller,
    Headphones,
    Watch,
    Computer,
    Phone
}
