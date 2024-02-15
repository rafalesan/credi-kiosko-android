package com.rafalesan.products.domain.validator

import com.rafalesan.products.R

object ProductValidator {

    fun validateProductInputs(
        productName: String?,
        productPrice: String?
    ): List<ProductInputValidation> {
        return listOfNotNull(
            validateProductName(productName),
            validateProductPrice(productPrice)
        )
    }

    private fun validateProductName(productName: String?): ProductInputValidation? {
        if (productName.isNullOrBlank()) {
            return ProductInputValidation.EMPTY_PRODUCT_NAME;
        }
        return null
    }

    private fun validateProductPrice(productPrice: String?): ProductInputValidation? {
        if (productPrice.isNullOrBlank()) {
            return ProductInputValidation.EMPTY_PRODUCT_PRICE;
        }
        return null
    }

    enum class ProductInputValidation(val errorResId: Int) {
        EMPTY_PRODUCT_NAME(R.string.empty_product_name_validation_desc),
        EMPTY_PRODUCT_PRICE(R.string.empty_product_price_validation_desc)
    }

}
