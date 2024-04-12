package com.rafalesan.credikiosko.core.bluetooth_printer.domain.repository

import com.rafalesan.credikiosko.core.bluetooth_printer.domain.entity.BluetoothDevice
import com.rafalesan.credikiosko.core.commons.domain.utils.Result

interface IBluetoothDeviceRepository {
    fun getBondedBluetoothDevices() : Result<List<BluetoothDevice>>
}
