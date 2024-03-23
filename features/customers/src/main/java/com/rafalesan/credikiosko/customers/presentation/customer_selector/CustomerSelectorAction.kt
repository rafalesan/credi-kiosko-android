package com.rafalesan.credikiosko.customers.presentation.customer_selector

import com.rafalesan.credikiosko.core.commons.presentation.models.CustomerParcelable

sealed class CustomerSelectorAction {

    class ReturnCustomer(val customer: CustomerParcelable): CustomerSelectorAction()

}
