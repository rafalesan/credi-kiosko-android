package com.rafalesan.credikiosko.core.bluetooth_printer.data.datasource

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import androidx.core.content.ContextCompat
import com.rafalesan.credikiosko.core.bluetooth_printer.data.exceptions.BluetoothNotSupported
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class BluetoothDataSource @Inject constructor(
    @ApplicationContext val context: Context,
) {

    @SuppressLint("MissingPermission")
    fun getBluetoothDevices(): List<BluetoothDevice> {
        val bluetoothManager = ContextCompat.getSystemService(
            context,
            BluetoothManager::class.java
        ) ?: run {
            throw BluetoothNotSupported()
        }

        return bluetoothManager.adapter.bondedDevices.toList()

    }

}
