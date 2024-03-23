package com.rafalesan.credikiosko.customers.presentation.customer_selector

import com.rafalesan.credikiosko.core.commons.domain.entity.Customer

sealed class CustomerSelectorEvent {

    class CustomerSelected(val customer: Customer) : CustomerSelectorEvent()
    
}
