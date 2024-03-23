package com.rafalesan.credikiosko.credits.domain.usecase

import java.math.BigDecimal
import javax.inject.Inject

class CalculateProductLineTotalUseCase @Inject constructor() {

    operator fun invoke(
        productPrice: String?,
        productsQuantity: String?
    ): String {

        if (productPrice.isNullOrBlank() || productsQuantity.isNullOrBlank()) {
            return 0.toString()
        }

        val price = BigDecimal(productPrice)
        val quantity = BigDecimal(productsQuantity)
        val total = price * quantity

        return total.toString()

    }

}