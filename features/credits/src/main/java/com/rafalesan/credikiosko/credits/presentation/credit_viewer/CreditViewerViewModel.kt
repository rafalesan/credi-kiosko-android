package com.rafalesan.credikiosko.credits.presentation.credit_viewer

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.rafalesan.credikiosko.core.commons.creditIdNavKey
import com.rafalesan.credikiosko.core.commons.presentation.base.BaseViewModel
import com.rafalesan.credikiosko.credits.domain.usecase.FindCreditUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CreditViewerViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val findCreditUseCase: FindCreditUseCase
) : BaseViewModel() {

    private val _viewState = MutableStateFlow(CreditViewerState())
    val viewState = _viewState.asStateFlow()

    init {
        loadCreditFrom(savedStateHandle)
    }

    fun perform(event: CreditViewerEvent) {
        when (event) {
            CreditViewerEvent.PrintCredit -> handlePrintCreditEvent()
        }
    }

    private fun handlePrintCreditEvent() {
        toast("En construcción")
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
                    customerName = customer.name,
                    creditDateTime = credit.date,
                    creditTotal = credit.total,
                    creditProducts = products
                )
            }
        }
    }

}
