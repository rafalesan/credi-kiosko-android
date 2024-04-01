package com.rafalesan.credikiosko.core.bluetooth_printer

import android.annotation.SuppressLint
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.util.UUID

class BluetoothPrinterConnection(
    private val bluetoothManager: BluetoothManager
) {

    private val defaultPrinter = BluetoothPrinter(
        name = "Bluetooth Printer",
        macAddress = "66:22:DF:ED:8D:AE"
    )

    lateinit var bluetoothSocket: BluetoothSocket
    private val bluetoothPrinterUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")

    @SuppressLint("MissingPermission")
    suspend fun connectDevice(
        onConnectionError: (Exception) -> Unit,
        onConnected: () -> Unit
    ) {

        val device = bluetoothManager.adapter.getRemoteDevice(defaultPrinter.macAddress)

        bluetoothSocket = device.createRfcommSocketToServiceRecord(
            bluetoothPrinterUUID
        )

        try {
            withContext(Dispatchers.IO) {
                bluetoothSocket.connect()
                delay(500)
            }
            onConnected()

        } catch (ex: Exception) {
            Timber.e(ex)
            onConnectionError(ex)
        }

    }
}
