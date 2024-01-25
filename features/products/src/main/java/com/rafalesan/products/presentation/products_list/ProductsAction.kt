package com.rafalesan.products.presentation.products_list

import com.rafalesan.products.domain.entity.Product

sealed class ProductsAction {
    class ShowProductForm(val product: Product? = null): ProductsAction()
}
