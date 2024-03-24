package com.rafalesan.credikiosko.credits.presentation.credit_form

import com.rafalesan.credikiosko.core.commons.domain.entity.CreditProduct
import com.rafalesan.credikiosko.core.commons.domain.entity.Customer

data class CreditFormState(
    val customerSelected: Customer? = null,
    val customerNameSelectedError: Int? = null,
    val productLines: List<CreditProduct> = emptyList()
)
