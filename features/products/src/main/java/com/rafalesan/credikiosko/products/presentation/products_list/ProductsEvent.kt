package com.rafalesan.credikiosko.products.presentation.products_list

import com.rafalesan.credikiosko.core.commons.domain.entity.Product

sealed class ProductsEvent {

    data object CreateNewProduct: ProductsEvent()
    class ShowProduct(val product: Product): ProductsEvent()

}
