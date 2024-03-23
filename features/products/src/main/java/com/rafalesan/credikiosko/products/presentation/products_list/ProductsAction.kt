package com.rafalesan.credikiosko.products.presentation.products_list

import com.rafalesan.credikiosko.core.commons.domain.entity.Product

sealed class ProductsAction {
    class ShowProductForm(val product: Product? = null): ProductsAction()
}
