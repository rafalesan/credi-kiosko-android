package com.rafalesan.credikiosko.customers.presentation.customer_form

sealed class CustomerFormEvent {
    class SetCustomerName(val customerName: String): CustomerFormEvent()
    class SetCustomerNickname(val customerNickname: String): CustomerFormEvent()
    class SetCustomerEmail(val customerEmail: String): CustomerFormEvent()
    data object SaveCustomer: CustomerFormEvent()
    data object DeleteCustomer: CustomerFormEvent()
}
