package com.rafalesan.credikiosko.customers.presentation.customers_list

import androidx.paging.PagingData
import com.rafalesan.credikiosko.core.commons.presentation.base.BaseViewModel
import com.rafalesan.credikiosko.customers.domain.entity.Customer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CustomersViewModel @Inject constructor(

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
        _customerList.value = PagingData.from(
            listOf(
                Customer(1, "Rafael Antonio Alegría Sánchez", "Pito"),
                Customer(2, "Gloria María Sánchez Muñoz", "Doña Gloria"),
                Customer(3, "Darling Lorena Alegría Sánchez", "Tierna")
            )
        )
    }

}
