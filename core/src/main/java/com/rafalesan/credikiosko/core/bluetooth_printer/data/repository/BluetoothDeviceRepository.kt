package com.rafalesan.credikiosko.core.bluetooth_printer.data.repository

import com.rafalesan.credikiosko.core.bluetooth_printer.data.datasource.BluetoothDataSource
import com.rafalesan.credikiosko.core.bluetooth_printer.data.mappers.toBluetoothDeviceDomain
import com.rafalesan.credikiosko.core.bluetooth_printer.domain.entity.BluetoothDevice
import com.rafalesan.credikiosko.core.bluetooth_printer.domain.repository.IBluetoothDeviceRepository
import com.rafalesan.credikiosko.core.commons.domain.utils.Result

class BluetoothDeviceRepository(
    private val bluetoothDataSource: BluetoothDataSource
) : IBluetoothDeviceRepository {

    override fun getBondedBluetoothDevices(): Result<List<BluetoothDevice>> {
        return try {
            val androidBluetoothDevices = bluetoothDataSource.getBluetoothDevices()
            val bluetoothDevicesDomain = androidBluetoothDevices.map {
                it.toBluetoothDeviceDomain()
            }
            Result.Success(bluetoothDevicesDomain)
        } catch (ex: Exception) {
            Result.Error(ex)
        }
    }

}
