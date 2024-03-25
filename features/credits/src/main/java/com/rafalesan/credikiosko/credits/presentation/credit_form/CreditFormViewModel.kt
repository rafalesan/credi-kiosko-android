package com.rafalesan.credikiosko.credits.presentation.credit_form

import androidx.lifecycle.viewModelScope
import com.rafalesan.credikiosko.core.commons.domain.entity.CreditProduct
import com.rafalesan.credikiosko.core.commons.presentation.base.BaseViewModel
import com.rafalesan.credikiosko.core.commons.presentation.mappers.toCreditProductDomain
import com.rafalesan.credikiosko.core.commons.presentation.mappers.toCreditProductParcelable
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
            CreditFormEvent.AddProductLinePressed -> handleAddProductLineEvent()
            is CreditFormEvent.DeleteProductLine -> handleDeleteProductLineEvent(event.creditProduct)
            is CreditFormEvent.EditProductLine -> handleEditProductLineEvent(event.creditProduct)
            is CreditFormEvent.SetCustomer -> handleSetCustomerEvent(event.customer)
            is CreditFormEvent.AddOrReplaceCreditProduct -> handleAddOrReplaceCreditProductEvent(event.creditProduct)
        }
    }

    private fun handleAddOrReplaceCreditProductEvent(creditProduct: CreditProductParcelable) {
        if (creditProduct.productId == zeroLong) {
            return
        }

        if (creditProduct.id != zeroLong) {
            replaceCreditProductWith(creditProduct.toCreditProductDomain())
            return
        }

        addCreditProduct(creditProduct.toCreditProductDomain())

    }

    private fun replaceCreditProductWith(creditProduct: CreditProduct) {
        _viewState.update { creditFormState ->
            val currentProductLines = creditFormState.productLines.toMutableList()

            val indexForReplace = currentProductLines.indexOfFirst {
                it.id == creditProduct.id
            }

            currentProductLines[indexForReplace] = creditProduct

            creditFormState.copy(
                productLines = currentProductLines
            )

        }
    }

    private fun addCreditProduct(creditProduct: CreditProduct) {
        _viewState.update { creditFormState ->

            val currentProductLines = creditFormState.productLines
            val temporalId = currentProductLines.size + 1
            val newCreditProductToAdd = creditProduct
                .copy(id = temporalId.toLong())

            creditFormState.copy(
                productLines = creditFormState.productLines + newCreditProductToAdd
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
        viewModelScope.launch {
            _action.send(
                CreditFormAction.ShowCreditProductForm(creditProduct.toCreditProductParcelable())
            )
        }
    }

    private fun handleDeleteProductLineEvent(creditProduct: CreditProduct) {
        _viewState.update {
            val currentProductLines = it.productLines.toMutableList()
            currentProductLines.remove(creditProduct)
            it.copy(
                productLines = currentProductLines
            )
        }
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
            _action.send(CreditFormAction.ShowCreditProductForm())
        }
    }

}