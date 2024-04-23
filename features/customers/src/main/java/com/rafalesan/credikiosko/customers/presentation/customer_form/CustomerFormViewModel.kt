package com.rafalesan.credikiosko.customers.presentation.customer_form

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.rafalesan.credikiosko.core.commons.domain.entity.Customer
import com.rafalesan.credikiosko.core.commons.domain.utils.ResultOf
import com.rafalesan.credikiosko.core.commons.emptyString
import com.rafalesan.credikiosko.core.commons.presentation.base.BaseViewModel
import com.rafalesan.credikiosko.core.commons.zeroLong
import com.rafalesan.credikiosko.customers.domain.usecase.DeleteCustomerUseCase
import com.rafalesan.credikiosko.customers.domain.usecase.SaveCustomerUseCase
import com.rafalesan.credikiosko.customers.domain.validator.CustomerInputsValidator
import com.rafalesan.credikiosko.customers.domain.validator.CustomerInputsValidator.CustomerInputValidation.EMPTY_CUSTOMER_NAME
import com.rafalesan.credikiosko.customers.domain.validator.CustomerInputsValidator.CustomerInputValidation.INVALID_CUSTOMER_EMAIL
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
class CustomerFormViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val saveCustomerUseCase: SaveCustomerUseCase,
    private val deleteCustomerUseCase: DeleteCustomerUseCase
): BaseViewModel() {

    private val _viewState = MutableStateFlow(CustomerFormState())
    val viewState = _viewState.asStateFlow()

    private val _action = Channel<CustomerFormAction>(Channel.BUFFERED)
    val action = _action.receiveAsFlow()

    init {
        val customerId = savedStateHandle.get<String?>("customer_id")
        val customerName = savedStateHandle.get<String?>("customer_name")
        val customerNickname = savedStateHandle.get<String?>("customer_nickname")
        val customerEmail = savedStateHandle.get<String?>("customer_email")

        _viewState.update {
            it.copy(
                customerId = customerId?.toLong() ?: zeroLong,
                customerName = customerName ?: emptyString,
                customerNickname = customerNickname ?: emptyString,
                customerEmail = customerEmail ?: emptyString,
                isNewCustomer = customerId == null
            )
        }
    }

    fun perform(event: CustomerFormEvent) {
        when (event) {
            is CustomerFormEvent.SetCustomerName -> _viewState.update {
                it.copy(
                    customerName = event.customerName,
                    customerNameError = null
                )
            }
            is CustomerFormEvent.SetCustomerNickname -> _viewState.update {
                it.copy(customerNickname = event.customerNickname)
            }
            is CustomerFormEvent.SetCustomerEmail -> _viewState.update {
                it.copy(
                    customerEmail = event.customerEmail,
                    customerEmailError = null
                )
            }
            CustomerFormEvent.SaveCustomer -> handleSaveCustomer()
            CustomerFormEvent.DeleteCustomer -> handleDeleteCustomer()
        }
    }

    private fun handleSaveCustomer() {
        viewModelScope.launch {
            clearInputValidations()
            val customer = buildCustomerFromViewState()
            val result = saveCustomerUseCase.invoke(customer)

            when (result) {
                is ResultOf.Success -> goBackToCustomerList()
                is ResultOf.Failure.InvalidData -> handleInvalidData(result.validations)
                else -> { Timber.e("Operation not supported: $result") }
            }

        }
    }

    private fun handleInvalidData(validations: List<CustomerInputsValidator.CustomerInputValidation>) {
        validations.forEach {  validation ->
            when (validation) {
                EMPTY_CUSTOMER_NAME -> _viewState.update {
                    it.copy(customerNameError = validation.errorResId)
                }
                INVALID_CUSTOMER_EMAIL -> _viewState.update {
                    it.copy(customerEmailError = validation.errorResId)
                }
            }
        }
    }

    private fun handleDeleteCustomer() {
        viewModelScope.launch {
            val customer = buildCustomerFromViewState()
            deleteCustomerUseCase.invoke(customer)
            goBackToCustomerList()
        }
    }

    private fun clearInputValidations() {
        _viewState.update {
            it.copy(
                customerNameError = null,
                customerEmailError = null
            )
        }
    }

    private suspend fun goBackToCustomerList() {
        _action.send(CustomerFormAction.ReturnToCustomerList)
    }

    private fun buildCustomerFromViewState(): Customer {
        return with (viewState.value) {
            Customer(
                customerId,
                customerName,
                customerNickname,
                customerEmail
            )
        }
    }

}
