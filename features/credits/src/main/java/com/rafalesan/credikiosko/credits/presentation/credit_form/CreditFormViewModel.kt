package com.rafalesan.credikiosko.credits.presentation.credit_form

import androidx.lifecycle.viewModelScope
import com.rafalesan.credikiosko.core.commons.presentation.base.BaseViewModel
import com.rafalesan.credikiosko.core.commons.presentation.mappers.toCustomerDomain
import com.rafalesan.credikiosko.core.commons.presentation.models.CustomerParcelable
import com.rafalesan.credikiosko.core.commons.zeroLong
import com.rafalesan.credikiosko.credits.domain.entity.CreditProduct
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
class CreditFormViewModel @Inject constructor(

) : BaseViewModel() {

    private val _viewState = MutableStateFlow(CreditFormState())
    val viewState = _viewState.asStateFlow()

    private val _action = Channel<CreditFormAction>(Channel.BUFFERED)
    val action = _action.receiveAsFlow()

    fun perform(event: CreditFormEvent) {
        when (event) {
            CreditFormEvent.CustomerSelectorPressed -> handleCustomerSelectorPressed()
            CreditFormEvent.CreateCredit -> handleCreateCreditEvent()
            CreditFormEvent.AddProductLine -> handleAddProductLineEvent()
            is CreditFormEvent.DeleteProductLine -> handleDeleteProductLineEvent(event.creditProduct)
            is CreditFormEvent.EditProductLine -> handleEditProductLineEvent(event.creditProduct)
            is CreditFormEvent.SetCustomer -> handleSetCustomerEvent(event.customer)
        }
    }

    private fun handleSetCustomerEvent(customer: CustomerParcelable) {
        if (customer.id == zeroLong) {
            return
        }
        Timber.d("setting customer: ${customer.name} - ${customer.id}")
        _viewState.update {
            it.copy(
                customerSelected = customer.toCustomerDomain()
            )
        }
        Timber.d(viewState.value.toString())
    }

    private fun handleEditProductLineEvent(creditProduct: CreditProduct) {
        toast("En Construcción (${creditProduct.productId})")
    }

    private fun handleDeleteProductLineEvent(creditProduct: CreditProduct) {
        toast("En Construcción (${creditProduct.productId})")
    }

    private fun handleCustomerSelectorPressed() {
        viewModelScope.launch {
            _action.send(CreditFormAction.ShowCustomerSelector)
        }
    }

    private fun handleCreateCreditEvent() {
        toast("Customer Selected: ${viewState.value.customerSelected?.name}")
    }

    private fun handleAddProductLineEvent() {
        toast("En Contrucción")
    }

}