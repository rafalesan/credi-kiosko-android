package com.rafalesan.credikiosko.core.bluetooth_printer.data.models

enum class PrintAlignment(val byteArray: ByteArray) {
    FEED_LINE(byteArrayOf(10)),
    LEFT(byteArrayOf(0x1b, 'a'.code.toByte(), 0x00)),
    CENTER(byteArrayOf(0x1b, 'a'.code.toByte(), 0x01)),
    RIGHT(byteArrayOf(0x1b, 'a'.code.toByte(), 0x02)),
}