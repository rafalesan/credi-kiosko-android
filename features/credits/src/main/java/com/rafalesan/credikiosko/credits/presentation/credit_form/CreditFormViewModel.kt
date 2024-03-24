package com.rafalesan.credikiosko.credits.presentation.credit_form

import androidx.lifecycle.viewModelScope
import com.rafalesan.credikiosko.core.commons.domain.entity.CreditProduct
import com.rafalesan.credikiosko.core.commons.presentation.base.BaseViewModel
import com.rafalesan.credikiosko.core.commons.presentation.mappers.toCreditProductDomain
import com.rafalesan.credikiosko.core.commons.presentation.mappers.toCustomerDomain
import com.rafalesan.credikiosko.core.commons.presentation.models.CreditProductParcelable
import com.rafalesan.credikiosko.core.commons.presentation.models.CustomerParcelable
import com.rafalesan.credikiosko.core.commons.zeroLong
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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
            is CreditFormEvent.AddCreditProduct -> handleAddCreditProductEvent(event.creditProduct)
        }
    }

    private fun handleAddCreditProductEvent(creditProduct: CreditProductParcelable) {
        if (creditProduct.productId == zeroLong) {
            return
        }
        _viewState.update {
            it.copy(
                productLines = it.productLines + creditProduct.toCreditProductDomain()
            )
        }
    }

    private fun handleSetCustomerEvent(customer: CustomerParcelable) {
        if (customer.id == zeroLong) {
            return
        }
        _viewState.update {
            it.copy(
                customerSelected = customer.toCustomerDomain()
            )
        }
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
        viewModelScope.launch {
            _action.send(CreditFormAction.ShowCreditProductForm)
        }
    }

}