package com.rafalesan.products.presentation.products_list

import com.rafalesan.products.domain.entity.Product

sealed class ProductsEvent {

    data object CreateNewProduct: ProductsEvent()
    class ShowProduct(val product: Product): ProductsEvent()

}
