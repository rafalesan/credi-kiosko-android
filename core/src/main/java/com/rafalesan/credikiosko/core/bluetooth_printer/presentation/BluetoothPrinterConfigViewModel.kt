package com.rafalesan.credikiosko.core.bluetooth_printer.presentation

import androidx.lifecycle.viewModelScope
import com.rafalesan.credikiosko.core.bluetooth_printer.domain.entity.BluetoothDevice
import com.rafalesan.credikiosko.core.bluetooth_printer.domain.usecases.GetBondedBluetoothDevicesUseCase
import com.rafalesan.credikiosko.core.bluetooth_printer.domain.usecases.SavePrinterUseCase
import com.rafalesan.credikiosko.core.commons.domain.utils.Result
import com.rafalesan.credikiosko.core.commons.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class BluetoothPrinterConfigViewModel @Inject constructor(
    private val getBondedBluetoothDevicesUseCase: GetBondedBluetoothDevicesUseCase,
    private val savePrinterUseCase: SavePrinterUseCase
) : BaseViewModel() {

    private val _viewState = MutableStateFlow(BluetoothPrinterConfigViewState())
    val viewState = _viewState.asStateFlow()

    init {
        fetchBluetoothDevices()
    }

    fun perform(event: BluetoothPrinterConfigEvent) {
        when (event) {
            is BluetoothPrinterConfigEvent.BluetoothDevicePressed -> {
                handleBluetoothDevicePressedEvent(event.bluetoothDevice)
            }
        }
    }

    private fun handleBluetoothDevicePressedEvent(bluetoothDevice: BluetoothDevice) {
        viewModelScope.launch {
            savePrinterUseCase(bluetoothDevice)
            _viewState.update {
                it.copy(
                    isPrinterConfigured = true
                )
            }
        }
    }

    private fun fetchBluetoothDevices() {
        val result = getBondedBluetoothDevicesUseCase()

        when (result) {
            is Result.Success -> _viewState.update {
                it.copy(
                    bondedBluetoothDevices = result.data
                )
            }
            is Result.Error -> {
                Timber.e(result.exception)
            }
        }


    }

}
