package com.rafalesan.credikiosko.credits.presentation.credit_form

import androidx.lifecycle.viewModelScope
import com.rafalesan.credikiosko.core.commons.domain.entity.CreditProduct
import com.rafalesan.credikiosko.core.commons.domain.utils.ResultOf
import com.rafalesan.credikiosko.core.commons.presentation.base.BaseViewModel
import com.rafalesan.credikiosko.core.commons.presentation.mappers.toCreditProductDomain
import com.rafalesan.credikiosko.core.commons.presentation.mappers.toCreditProductParcelable
import com.rafalesan.credikiosko.core.commons.presentation.mappers.toCustomerDomain
import com.rafalesan.credikiosko.core.commons.presentation.models.CreditProductParcelable
import com.rafalesan.credikiosko.core.commons.presentation.models.CustomerParcelable
import com.rafalesan.credikiosko.core.commons.presentation.utils.DateFormatUtil
import com.rafalesan.credikiosko.core.commons.zeroLong
import com.rafalesan.credikiosko.credits.domain.entity.Credit
import com.rafalesan.credikiosko.credits.domain.usecase.SaveCreditUseCase
import com.rafalesan.credikiosko.credits.domain.validator.CreditInputsValidator
import com.rafalesan.credikiosko.credits.domain.validator.CreditInputsValidator.CreditInputValidation.EMPTY_CREDIT_PRODUCT_LINES
import com.rafalesan.credikiosko.credits.domain.validator.CreditInputsValidator.CreditInputValidation.EMPTY_CUSTOMER
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
    private val saveCreditUseCase: SaveCreditUseCase
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
        } else {
            addCreditProduct(creditProduct.toCreditProductDomain())
        }

        calculateTotalCreditAmount()
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
                productLines = creditFormState.productLines + newCreditProductToAdd,
                productLinesError = null
            )
        }
    }

    private fun handleSetCustomerEvent(customer: CustomerParcelable) {
        if (customer.id == zeroLong) {
            return
        }
        _viewState.update {
            it.copy(
                customerSelected = customer.toCustomerDomain(),
                customerNameSelectedError = null
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
        calculateTotalCreditAmount()
    }

    private fun handleCustomerSelectorPressed() {
        viewModelScope.launch {
            _action.send(CreditFormAction.ShowCustomerSelector)
        }
    }

    private fun handleCreateCreditEvent() {

        clearInputValidations()

        val credit = buildCreditFromViewState()
        val productLines = buildProductLinesResetIds()

        viewModelScope.launch {
            val result = saveCreditUseCase(
                credit,
                productLines
            )

            when (result) {
                is ResultOf.Success -> {
                    val savedCreditId = result.value
                    _action.send(CreditFormAction.ShowCreditViewer(savedCreditId))
                }
                is ResultOf.Failure.InvalidData -> {
                    handleInvalidData(result.validations)
                }
                else -> { Timber.e("Operation not supported: $result") }
            }


        }

    }

    private fun clearInputValidations() {
        _viewState.update {
            it.copy(
                customerNameSelectedError = null,
                productLinesError = null
            )
        }
    }

    private fun handleInvalidData(
        validations: List<CreditInputsValidator.CreditInputValidation>
    ) {
        validations.forEach { validation ->
            when (validation) {
                EMPTY_CUSTOMER -> _viewState.update {
                    it.copy(customerNameSelectedError = validation.errorResId)
                }
                EMPTY_CREDIT_PRODUCT_LINES -> _viewState.update {
                    it.copy(productLinesError = validation.errorResId)
                }
            }
        }
    }

    private fun handleAddProductLineEvent() {
        viewModelScope.launch {
            _action.send(CreditFormAction.ShowCreditProductForm())
        }
    }

    private fun calculateTotalCreditAmount() {
        _viewState.update {
            val totalAmount = it.productLines.sumOf { product -> product.total.toBigDecimal() }
            it.copy(totalCreditAmount = totalAmount.toString())
        }

    }

    private fun buildCreditFromViewState(): Credit {
        return with(viewState.value) {
            Credit(
                businessId = zeroLong,
                customerId = customerSelected?.id ?: zeroLong,
                date = DateFormatUtil.getCurrentDateString(),
                total = totalCreditAmount
            )
        }
    }

    private fun buildProductLinesResetIds(): List<CreditProduct> {
        return viewState
            .value
            .productLines
            .map { it.copy(id = zeroLong) }
    }

}