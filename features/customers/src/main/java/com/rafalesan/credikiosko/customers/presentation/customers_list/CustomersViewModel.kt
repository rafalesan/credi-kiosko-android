package com.rafalesan.credikiosko.customers.presentation.customers_list

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rafalesan.credikiosko.core.commons.presentation.base.BaseViewModel
import com.rafalesan.credikiosko.customers.domain.entity.Customer
import com.rafalesan.credikiosko.customers.domain.usecase.GetLocalCustomersPagedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomersViewModel @Inject constructor(
    private val getLocalCustomersPagedUseCase: GetLocalCustomersPagedUseCase
) : BaseViewModel() {

    private val _customerList = MutableStateFlow<PagingData<Customer>>(PagingData.empty())
    val customerList = _customerList.asStateFlow()

    init {
        fetchLocalCustomers()
    }

    fun perform(customersEvent: CustomersEvent) {
        when (customersEvent) {
            CustomersEvent.CreateNewCustomer -> handleCreateNewCustomerEvent()
            is CustomersEvent.ShowCustomer -> handleShowCustomerEvent(customersEvent.customer)
        }
    }

    private fun handleShowCustomerEvent(customer: Customer) {
        toast("En construcción")
    }

    private fun handleCreateNewCustomerEvent() {
        toast("En construcción")
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
