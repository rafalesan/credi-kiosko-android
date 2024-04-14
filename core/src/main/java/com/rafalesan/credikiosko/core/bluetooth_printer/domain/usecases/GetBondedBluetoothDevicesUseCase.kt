package com.rafalesan.credikiosko.core.bluetooth_printer.domain.usecases

import com.rafalesan.credikiosko.core.bluetooth_printer.domain.entity.BluetoothDevice
import com.rafalesan.credikiosko.core.bluetooth_printer.domain.entity.DeviceType
import com.rafalesan.credikiosko.core.bluetooth_printer.domain.repository.IBluetoothDeviceRepository
import com.rafalesan.credikiosko.core.commons.domain.utils.Result
import javax.inject.Inject

class GetBondedBluetoothDevicesUseCase @Inject constructor(
    private val bluetoothDeviceRepository: IBluetoothDeviceRepository
) {

    operator fun invoke(showOnlyPrinters: Boolean = true): Result<List<BluetoothDevice>> {

        val result = bluetoothDeviceRepository.getBondedBluetoothDevices()

        if (
            result is Result.Success &&
            showOnlyPrinters
        ) {
            val bondedDevices = result.data
            return Result.Success(bondedDevices.filter { it.type == DeviceType.Printer })
        }

        return result

    }

}
