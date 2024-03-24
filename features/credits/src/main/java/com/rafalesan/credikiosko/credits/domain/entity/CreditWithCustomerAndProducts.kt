package com.rafalesan.credikiosko.credits.domain.entity

import com.rafalesan.credikiosko.core.commons.domain.entity.CreditProduct
import com.rafalesan.credikiosko.core.commons.domain.entity.Customer

data class CreditWithCustomerAndProducts(
    val credit: Credit,
    val customer: Customer,
    val products: List<CreditProduct>
)
