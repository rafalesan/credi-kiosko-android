package com.rafalesan.credikiosko.customers.domain.validator

import com.rafalesan.credikiosko.core.commons.domain.utils.ext.isNotValidEmail
import com.rafalesan.credikiosko.customers.R

object CustomerInputsValidator {

    fun validateCustomerInputs(
        customerName: String?,
        customerEmail: String?
    ): List<CustomerInputValidation> {
        return listOfNotNull(
            validateCustomerName(customerName),
            validateCustomerEmail(customerEmail)
        )
    }

    private fun validateCustomerName(customerName: String?): CustomerInputValidation? {
        if (customerName.isNullOrBlank()) {
            return CustomerInputValidation.EMPTY_CUSTOMER_NAME
        }
        return null
    }

    private fun validateCustomerEmail(customerEmail: String?): CustomerInputValidation? {
        if (customerEmail.isNullOrEmpty()) {
            return null
        }
        if (customerEmail.isNotValidEmail()) {
            return CustomerInputValidation.INVALID_CUSTOMER_EMAIL
        }
        return null
    }

    enum class CustomerInputValidation(val errorResId: Int) {
        EMPTY_CUSTOMER_NAME(R.string.empty_customer_name_validation_desc),
        INVALID_CUSTOMER_EMAIL(R.string.invalid_customer_email_validation_desc)
    }

}
