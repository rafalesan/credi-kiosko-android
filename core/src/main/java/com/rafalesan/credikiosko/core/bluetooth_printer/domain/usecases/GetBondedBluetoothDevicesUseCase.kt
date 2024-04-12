package com.rafalesan.credikiosko.core.bluetooth_printer.domain.usecases

import com.rafalesan.credikiosko.core.bluetooth_printer.domain.entity.BluetoothDevice
import com.rafalesan.credikiosko.core.bluetooth_printer.domain.repository.IBluetoothDeviceRepository
import com.rafalesan.credikiosko.core.commons.domain.utils.Result
import javax.inject.Inject

class GetBondedBluetoothDevicesUseCase @Inject constructor(
    private val bluetoothDeviceRepository: IBluetoothDeviceRepository
) {

    operator fun invoke(): Result<List<BluetoothDevice>> {
        return bluetoothDeviceRepository.getBondedBluetoothDevices()
    }

}
