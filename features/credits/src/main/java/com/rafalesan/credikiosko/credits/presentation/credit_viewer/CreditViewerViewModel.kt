package com.rafalesan.credikiosko.credits.presentation.credit_viewer

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.rafalesan.credikiosko.core.bluetooth_printer.data.models.PrintStatus
import com.rafalesan.credikiosko.core.bluetooth_printer.domain.usecases.IsBluetoothPrinterConfiguredUseCase
import com.rafalesan.credikiosko.core.commons.creditIdNavKey
import com.rafalesan.credikiosko.core.commons.presentation.base.BaseViewModel
import com.rafalesan.credikiosko.credits.R
import com.rafalesan.credikiosko.credits.domain.usecase.FindCreditUseCase
import com.rafalesan.credikiosko.credits.domain.usecase.PrintCreditUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CreditViewerViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val findCreditUseCase: FindCreditUseCase,
    private val printCreditUseCase: PrintCreditUseCase,
    private val isBluetoothPrinterConfiguredUseCase: IsBluetoothPrinterConfiguredUseCase
) : BaseViewModel() {

    private val _viewState = MutableStateFlow(CreditViewerState())
    val viewState = _viewState.asStateFlow()

    private val _action = Channel<CreditViewerAction>(Channel.BUFFERED)
    val action = _action.receiveAsFlow()

    init {
        loadCreditFrom(savedStateHandle)
    }

    fun perform(event: CreditViewerEvent) {
        when (event) {
            is CreditViewerEvent.PrintCredit -> handlePrintCreditEvent(event.checkBluetoothAvailability)
            CreditViewerEvent.CancelPrintingRetry -> handleCancelPrintingRetryEvent()
            CreditViewerEvent.RetryPrinting -> handleRetryPrintingEvent()
            CreditViewerEvent.CancelPrinterConfiguration -> handleCancelPrinterConfigurationEvent()
            CreditViewerEvent.StartPrinterConfiguration -> handleStartPrinterConfigurationEvent()
            is CreditViewerEvent.DismissPrinterConfiguration -> handleDismissPrinterConfigurationEvent(event.isPrinterConfigured)
        }
    }

    private fun handleDismissPrinterConfigurationEvent(isPrinterConfigured: Boolean) {
        _viewState.update {
            it.copy(isShowingPrinterConfiguration = false)
        }
        if (isPrinterConfigured) {
            handlePrintCreditEvent(
                checkBluetoothAvailability = true
            )
        }
    }

    private fun handleStartPrinterConfigurationEvent() {
        _viewState.update {
            it.copy(
                isShowingPrinterNotConfiguredMessage = false,
                isShowingPrinterConfiguration = true
            )
        }
    }

    private fun handleCancelPrinterConfigurationEvent() {
        _viewState.update {
            it.copy(isShowingPrinterNotConfiguredMessage = false)
        }
    }

    private fun handleRetryPrintingEvent() {
        _viewState.update {
            it.copy(printerConnectionError = null)
        }
        handlePrintCreditEvent(
            checkBluetoothAvailability = false
        )
    }

    private fun handleCancelPrintingRetryEvent() {
        _viewState.update {
            it.copy(printerConnectionError = null)
        }
    }

    private fun handlePrintCreditEvent(
        checkBluetoothAvailability: Boolean
    ) {

        if (checkBluetoothAvailability) {
            viewModelScope.launch {
                _action.send(CreditViewerAction.CheckBluetoothPermissionAndAvailability)
            }
            return
        }

        viewModelScope.launch {

            if (!isBluetoothPrinterConfiguredUseCase()) {
                _viewState.update {
                    it.copy(isShowingPrinterNotConfiguredMessage = true)
                }
                return@launch
            }

            printCreditUseCase(viewState.value.creditId)
                .collect { printStatus ->
                    val printLoadingTextId = when (printStatus) {
                        PrintStatus.ConnectingPrinter -> R.string.connecting_printer
                        PrintStatus.PrinterConnected -> R.string.printer_connected
                        PrintStatus.Printing -> R.string.printing
                        is PrintStatus.PrinterConnectionError -> {
                            _viewState.update {
                                it.copy(printerConnectionError = R.string.unable_to_connect_to_printer)
                            }
                            R.string.unable_to_connect_to_printer
                            null
                        }
                        PrintStatus.PrintSuccess -> null
                    }
                    _viewState.update {
                        it.copy(printLoadingStringResId = printLoadingTextId)
                    }
                }
        }
    }

    private fun loadCreditFrom(savedStateHandle: SavedStateHandle) {
        val creditId = savedStateHandle.get<Long>(creditIdNavKey) ?: run {
            Timber.e("creditId is null")
            return
        }
        viewModelScope.launch {
            val (credit, customer, products) = findCreditUseCase(creditId)
            _viewState.update {
                it.copy(
                    creditId = credit.id,
                    customerName = customer.name,
                    creditDateTime = credit.date,
                    creditTotal = credit.total,
                    creditProducts = products
                )
            }
        }
    }

}
