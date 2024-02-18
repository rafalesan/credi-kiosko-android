package com.rafalesan.credikiosko.customers.presentation.customer_form

import com.rafalesan.credikiosko.core.commons.emptyString
import com.rafalesan.credikiosko.core.commons.zeroLong

data class CustomerFormState(
    val customerId: Long = zeroLong,
    val customerName: String = emptyString,
    val customerNickname: String = emptyString,
    val customerEmail: String = emptyString,
    val customerNameError: Int? = null,
    val customerEmailError: Int? = null,
    val isNewCustomer: Boolean = true
)
