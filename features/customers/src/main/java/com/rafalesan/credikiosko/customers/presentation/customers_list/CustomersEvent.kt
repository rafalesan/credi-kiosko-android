package com.rafalesan.credikiosko.customers.presentation.customers_list

import com.rafalesan.credikiosko.customers.domain.entity.Customer

sealed class CustomersEvent {
    data object CreateNewCustomer: CustomersEvent()
    class ShowCustomer(val customer: Customer): CustomersEvent()
}
