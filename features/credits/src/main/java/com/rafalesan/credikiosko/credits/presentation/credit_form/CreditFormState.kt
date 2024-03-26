package com.rafalesan.credikiosko.credits.presentation.credit_form

import com.rafalesan.credikiosko.core.commons.domain.entity.CreditProduct
import com.rafalesan.credikiosko.core.commons.domain.entity.Customer
import com.rafalesan.credikiosko.core.commons.emptyString

data class CreditFormState(
    val customerSelected: Customer? = null,
    val customerNameSelectedError: Int? = null,
    val productLines: List<CreditProduct> = emptyList(),
    val productLinesError: Int? = null,
    val totalCreditAmount: String = emptyString
)
