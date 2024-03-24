package com.rafalesan.credikiosko.credits.domain.validator

import com.rafalesan.credikiosko.core.commons.zeroLong
import com.rafalesan.credikiosko.credits.R

object CreditProductInputsValidator {

    fun validateCreditProductInputs(
        productId: Long?,
        productPrice: String?,
        productsQuantity: String?
    ): List<CreditProductInputValidation> {
        return listOfNotNull(
            validateProductId(productId),
            validateProductPrice(productPrice),
            validateProductsQuantity(productsQuantity)
        )
    }

    private fun validateProductId(productId: Long?): CreditProductInputValidation? {
        if (productId == null || productId == zeroLong) {
            return CreditProductInputValidation.EMPTY_PRODUCT
        }
        return null
    }

    private fun validateProductPrice(productPrice: String?): CreditProductInputValidation? {
        if (productPrice.isNullOrBlank()) {
            return CreditProductInputValidation.EMPTY_PRODUCT_PRICE
        }

        val productPriceDouble = productPrice.toDouble()

        if (productPriceDouble <= 0) {
            return CreditProductInputValidation.INVALID_PRODUCT_PRICE_IS_ZERO
        }
        return null
    }

    private fun validateProductsQuantity(productsQuantity: String?): CreditProductInputValidation? {
        if (productsQuantity.isNullOrBlank()) {
            return CreditProductInputValidation.EMPTY_PRODUCTS_QUANTITY
        }

        val productsQuantityDouble = productsQuantity.toDouble()

        if (productsQuantityDouble <= 0) {
            return CreditProductInputValidation.INVALID_PRODUCTS_QUANTITY_IS_ZERO
        }
        return null
    }

    enum class CreditProductInputValidation(val errorResId: Int) {
        EMPTY_PRODUCT(R.string.empty_product_validation_desc),
        EMPTY_PRODUCT_PRICE(R.string.empty_product_price_valication_desc),
        EMPTY_PRODUCTS_QUANTITY(R.string.empty_products_quantity_validation_desc),
        INVALID_PRODUCT_PRICE_IS_ZERO(R.string.invalid_product_price_is_zero_validation_desc),
        INVALID_PRODUCTS_QUANTITY_IS_ZERO(R.string.invalid_products_quantity_is_zero_validation_desc)
    }

}
