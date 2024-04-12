package com.rafalesan.credikiosko.core.bluetooth_printer.domain.usecases

import com.rafalesan.credikiosko.core.bluetooth_printer.domain.entity.BluetoothDevice
import com.rafalesan.credikiosko.core.bluetooth_printer.domain.repository.IBluetoothPrinterRepository
import javax.inject.Inject

class SavePrinterUseCase @Inject constructor(
    private val bluetoothPrinterRepository: IBluetoothPrinterRepository
) {

    suspend operator fun invoke(bluetoothDevice: BluetoothDevice) {
        bluetoothPrinterRepository.savePrinter(bluetoothDevice)
    }

}