package com.rafalesan.credikiosko.products.presentation.product_form

sealed class ProductFormAction {
    data object ReturnToProductList: ProductFormAction()
}
