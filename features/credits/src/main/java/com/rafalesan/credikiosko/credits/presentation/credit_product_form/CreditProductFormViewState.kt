package com.rafalesan.credikiosko.credits.presentation.credit_product_form

import com.rafalesan.credikiosko.core.commons.domain.entity.Product
import com.rafalesan.credikiosko.core.commons.emptyString

data class CreditProductFormViewState(
    val productSelected: Product? = null,
    val productPrice: String? = null,
    val productsQuantity: String = 1.toString(),
    val productSelectedError: Int? = null,
    val productPriceError: Int? = null,
    val productsQuantityError: Int? = null,
    val totalAmount: String = emptyString
)
