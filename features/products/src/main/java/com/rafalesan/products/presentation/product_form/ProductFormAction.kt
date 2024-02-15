package com.rafalesan.products.presentation.product_form

sealed class ProductFormAction {
    data object ReturnToProductList: ProductFormAction()
}
