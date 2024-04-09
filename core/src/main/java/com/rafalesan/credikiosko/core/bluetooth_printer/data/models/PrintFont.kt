package com.rafalesan.credikiosko.core.bluetooth_printer.data.models

enum class PrintFont(val byteArray: ByteArray) {
    NORMAL(byteArrayOf(0x1B, 0x21, 0x03)),
    MEDIUM(byteArrayOf(0x1B, 0x21, 0x20)),
    LARGE(byteArrayOf(0x1B, 0x21, 0x10)),
    BOLD(byteArrayOf(0x1B, 0x21, 0x08))
}
