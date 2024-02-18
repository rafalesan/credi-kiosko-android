package com.rafalesan.credikiosko.customers.presentation.customers_list

import com.rafalesan.credikiosko.customers.domain.entity.Customer

sealed class CustomersAction {
    class ShowCustomerForm(val customer: Customer? = null): CustomersAction()
}
