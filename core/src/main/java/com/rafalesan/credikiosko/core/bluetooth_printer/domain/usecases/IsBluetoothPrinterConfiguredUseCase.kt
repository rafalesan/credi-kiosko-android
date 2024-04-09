package com.rafalesan.credikiosko.core.bluetooth_printer.domain.usecases

import com.rafalesan.credikiosko.core.bluetooth_printer.domain.repository.IBluetoothPrinterRepository
import javax.inject.Inject

class IsBluetoothPrinterConfiguredUseCase @Inject constructor(
    private val bluetoothPrinterRepository: IBluetoothPrinterRepository
) {

    suspend operator fun invoke(): Boolean {
        return bluetoothPrinterRepository.existsPrinterConfigured()
    }

}
