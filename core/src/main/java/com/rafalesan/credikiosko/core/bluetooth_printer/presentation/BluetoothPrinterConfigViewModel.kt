package com.rafalesan.credikiosko.core.bluetooth_printer.presentation

import android.bluetooth.BluetoothDevice
import androidx.lifecycle.viewModelScope
import com.rafalesan.credikiosko.core.bluetooth_printer.domain.usecases.SavePrinterUseCase
import com.rafalesan.credikiosko.core.bluetooth_printer.presentation.mappers.toBluetoothDeviceInfo
import com.rafalesan.credikiosko.core.bluetooth_printer.presentation.mappers.toBluetoothPrinter
import com.rafalesan.credikiosko.core.bluetooth_printer.presentation.model.BluetoothDeviceInfo
import com.rafalesan.credikiosko.core.commons.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BluetoothPrinterConfigViewModel @Inject constructor(
    private val savePrinterUseCase: SavePrinterUseCase
) : BaseViewModel() {

    private val _viewState = MutableStateFlow(BluetoothPrinterConfigViewState())
    val viewState = _viewState.asStateFlow()

    fun perform(event: BluetoothPrinterConfigEvent) {
        when (event) {
            is BluetoothPrinterConfigEvent.SetBondedBluetoothDevices -> handleSetBluetoothBondedDevices(event.devices)
            is BluetoothPrinterConfigEvent.BluetoothDevicePressed -> handleBluetoothDevicePressedEvent(event.bluetoothDeviceInfo)
        }
    }

    private fun handleBluetoothDevicePressedEvent(bluetoothDeviceInfo: BluetoothDeviceInfo) {
        viewModelScope.launch {
            savePrinterUseCase(bluetoothDeviceInfo.toBluetoothPrinter())
            _viewState.update {
                it.copy(
                    isPrinterConfigured = true
                )
            }
        }
    }

    private fun handleSetBluetoothBondedDevices(devices: List<BluetoothDevice>) {
        _viewState.update {
            it.copy(
                bondedBluetoothDevices = devices.map { device ->
                    device.toBluetoothDeviceInfo()
                }
            )
        }
    }

}
