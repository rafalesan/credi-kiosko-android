package com.rafalesan.products.presentation.product_form

data class ProductFormState(
    val productId: Long? = null,
    val productName: String = "",
    val productPrice: String = "",
    val productNameError: Int? = null,
    val productPriceError: Int? = null,
    val isNewProduct: Boolean = true
)
