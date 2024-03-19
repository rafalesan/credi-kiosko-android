package com.rafalesan.credikiosko.customers.presentation.customers_list

import com.rafalesan.credikiosko.core.commons.domain.entity.Customer

sealed class CustomersAction {
    class ShowCustomerForm(val customer: Customer? = null): CustomersAction()
}
