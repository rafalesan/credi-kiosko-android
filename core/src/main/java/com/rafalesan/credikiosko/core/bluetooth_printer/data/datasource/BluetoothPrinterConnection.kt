package com.rafalesan.credikiosko.core.bluetooth_printer.data.datasource

import android.annotation.SuppressLint
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import com.rafalesan.credikiosko.core.bluetooth_printer.data.exceptions.BluetoothConnectionFailException
import com.rafalesan.credikiosko.core.room.entity.BluetoothPrinterEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.util.UUID

class BluetoothPrinterConnection(
    private val bluetoothManager: BluetoothManager
) {

    lateinit var bluetoothSocket: BluetoothSocket
    private val bluetoothPrinterUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")

    @SuppressLint("MissingPermission")
    suspend fun connectDevice(
        bluetoothPrinter: BluetoothPrinterEntity?,
        onConnectionError: (Exception) -> Unit,
        onConnected: () -> Unit
    ) {

        val device = bluetoothManager.adapter.getRemoteDevice(
            bluetoothPrinter?.macAddress
        )

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
            onConnectionError(
                BluetoothConnectionFailException(device.name)
            )
        }

    }
}
