package com.rafalesan.products.presentation.product_form

sealed class ProductFormEvent {
    class SetProductName(val productName: String): ProductFormEvent()
    class SetProductPrice(val productPrice: String): ProductFormEvent()
    data object SaveProduct: ProductFormEvent()
    data object DeleteProduct: ProductFormEvent()
}
