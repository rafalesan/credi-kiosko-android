package com.rafalesan.credikiosko.credits.domain.validator

import com.rafalesan.credikiosko.core.commons.domain.entity.CreditProduct
import com.rafalesan.credikiosko.core.commons.zeroLong
import com.rafalesan.credikiosko.credits.R

object CreditInputsValidator {

    fun validateCreditInputs(
        customerId: Long?,
        creditProducts: List<CreditProduct>
    ): List<CreditInputValidation> {
        return listOfNotNull(
            validateCustomerInput(customerId),
            validateCreditProductLines(creditProducts)
        )
    }

    private fun validateCustomerInput(customerId: Long?): CreditInputValidation? {
        if (customerId == null || customerId == zeroLong) {
            return CreditInputValidation.EMPTY_CUSTOMER
        }
        return null
    }

    private fun validateCreditProductLines(creditProducts: List<CreditProduct>): CreditInputValidation? {
        if (creditProducts.isEmpty()) {
            return CreditInputValidation.EMPTY_CREDIT_PRODUCT_LINES
        }
        return null
    }

    enum class CreditInputValidation(val errorResId: Int) {
        EMPTY_CUSTOMER(R.string.empty_customer_validation_desc),
        EMPTY_CREDIT_PRODUCT_LINES(R.string.empty_credit_product_lines_validation_desc)
    }

}
