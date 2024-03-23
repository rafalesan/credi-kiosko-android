package com.rafalesan.credikiosko.customers.presentation.customer_selector

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rafalesan.credikiosko.core.commons.domain.entity.Customer
import com.rafalesan.credikiosko.core.commons.presentation.base.BaseViewModel
import com.rafalesan.credikiosko.core.commons.presentation.mappers.toCustomerParcelable
import com.rafalesan.credikiosko.customers.domain.usecase.GetLocalCustomersPagedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomerSelectorViewModel @Inject constructor(
    private val getLocalCustomersPagedUseCase: GetLocalCustomersPagedUseCase
) : BaseViewModel() {

    private val _customerList = MutableStateFlow<PagingData<Customer>>(PagingData.empty())
    val customerList = _customerList.asStateFlow()

    private val _action = Channel<CustomerSelectorAction>(Channel.BUFFERED)
    val action = _action.receiveAsFlow()

    init {
        fetchLocalCustomers()
    }

    fun perform(event: CustomerSelectorEvent) {
        when (event) {
            is CustomerSelectorEvent.CustomerSelected -> handleCustomerSelectedEvent(event.customer)
        }
    }

    private fun handleCustomerSelectedEvent(customer: Customer) {
        viewModelScope.launch {
            _action.send(
                CustomerSelectorAction.ReturnCustomer(customer.toCustomerParcelable())
            )
        }
    }

    private fun fetchLocalCustomers() {
        viewModelScope.launch {

            getLocalCustomersPagedUseCase()
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect {
                    _customerList.value = it
                }

        }
    }

}
