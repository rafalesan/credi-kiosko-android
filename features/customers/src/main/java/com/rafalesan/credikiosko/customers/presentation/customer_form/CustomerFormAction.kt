package com.rafalesan.credikiosko.customers.presentation.customer_form

sealed class CustomerFormAction {
    data object ReturnToCustomerList: CustomerFormAction()
}
