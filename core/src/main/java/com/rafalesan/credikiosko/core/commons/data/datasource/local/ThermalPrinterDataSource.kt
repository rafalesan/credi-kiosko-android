package com.rafalesan.credikiosko.core.commons.data.datasource.local

import android.bluetooth.BluetoothManager
import android.content.Context
import androidx.core.content.ContextCompat
import com.rafalesan.credikiosko.core.bluetooth_printer.BluetoothPrinterConnection
import com.rafalesan.credikiosko.core.bluetooth_printer.PrintAlignment
import com.rafalesan.credikiosko.core.bluetooth_printer.PrintFont
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ThermalPrinterDataSource @Inject constructor(
    @ApplicationContext val context: Context,
) {

    private val charsPerRowNormal = 42
    private val charsPerRowBold = charsPerRowNormal - 10
    private val supportAccentedChars = true

    private lateinit var bluetoothPrinterConnection: BluetoothPrinterConnection

    suspend fun connect(
        onConnectionError: (Exception) -> Unit,
        onConnected: () -> Unit
    ) {

        val bluetoothManager = ContextCompat.getSystemService(
            context,
            BluetoothManager::class.java
        ) ?: run {
            onConnectionError.invoke(
                Exception("This device does not support bluetooth")
            )
            return
        }


        bluetoothPrinterConnection = BluetoothPrinterConnection(
            bluetoothManager
        )

        bluetoothPrinterConnection.connectDevice(
            onConnectionError = onConnectionError,
            onConnected = onConnected
        )
    }

    fun fillLineWith(char: Char) {
        val fillStringLine = char
            .toString()
            .repeat(charsPerRowNormal)
        write(fillStringLine, PrintAlignment.CENTER)
    }

    fun write(
        text: String,
        alignment: PrintAlignment = PrintAlignment.LEFT,
        font: PrintFont = PrintFont.NORMAL
    ) {
        callPrinter(alignment.byteArray)
        callPrinter(font.byteArray)
        callPrinter(convertByteArray(text))
        printAndLine()
    }

    fun write(
        key: String,
        value: String,
        separator: Char = '.',
        alignment: PrintAlignment = PrintAlignment.CENTER,
        font: PrintFont = PrintFont.NORMAL
    ) {

        val charsPerRowToUse = when (font) {
            PrintFont.NORMAL,
            PrintFont.MEDIUM,
            PrintFont.LARGE -> charsPerRowNormal

            PrintFont.BOLD -> charsPerRowBold
        }

        val ans = key + value
        if (ans.length <= charsPerRowToUse) {
            val stringToPrint =
                key + separator.toString().repeat(charsPerRowToUse - ans.length) + value
            write(stringToPrint, alignment, font)
        } else {
            write("$key $value", alignment, font)
        }

    }

    fun newLine() {
        callPrinter(PrintAlignment.FEED_LINE.byteArray)
    }

    private fun callPrinter(bytes: ByteArray) {
        bluetoothPrinterConnection.bluetoothSocket.outputStream.write(bytes, 0, bytes.size)
    }

    private fun printAndLine() {
        bluetoothPrinterConnection.bluetoothSocket.outputStream.write(0x0A)
    }

    fun printLargeSpace() {
        newLine()
        newLine()
        newLine()
        newLine()
        bluetoothPrinterConnection.bluetoothSocket.outputStream.flush()
    }

    fun close() {
        bluetoothPrinterConnection.bluetoothSocket.close()
    }

    private fun convertByteArray(text: String): ByteArray {
        val b = ByteArray(text.length)
        if (supportAccentedChars) {
            for (i in text.indices) {
                b[i] = when (text[i]) {
                    'á' -> 160.toByte()
                    'é' -> 130.toByte()
                    'í' -> 161.toByte()
                    'ó' -> 162.toByte()
                    'ú' -> 163.toByte()
                    'Á' -> 65.toByte()
                    'É' -> 144.toByte()
                    'Í' -> 73.toByte()
                    'Ó' -> 79.toByte()
                    'Ú' -> 85.toByte()
                    'ñ' -> 164.toByte()
                    'Ñ' -> 165.toByte()
                    '°' -> 248.toByte()
                    '$' -> 36.toByte()
                    '¢' -> 155.toByte()
                    else -> text[i].code.toByte()
                }
            }
            return b
        }

        for (i in text.indices) {
            b[i] = when (text[i]) {
                'á' -> 'a'.code.toByte()
                'é' -> 'e'.code.toByte()
                'í' -> 'i'.code.toByte()
                'ó' -> 'o'.code.toByte()
                'ú' -> 'u'.code.toByte()
                'Á' -> 'A'.code.toByte()
                'É' -> 'E'.code.toByte()
                'Í' -> 'I'.code.toByte()
                'Ó' -> 'O'.code.toByte()
                'Ú' -> 'U'.code.toByte()
                'ñ' -> 'n'.code.toByte()
                'Ñ' -> 'N'.code.toByte()
                '°' -> 248.toByte()
                '$' -> 36.toByte()
                '¢' -> 155.toByte()
                else -> text[i].code.toByte()
            }
        }

        return b

    }

}
