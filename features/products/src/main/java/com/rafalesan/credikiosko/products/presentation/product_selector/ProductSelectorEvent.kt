package com.rafalesan.credikiosko.products.presentation.product_selector

import com.rafalesan.credikiosko.core.commons.domain.entity.Product

sealed class ProductSelectorEvent {

    class ProductSelected(val product: Product): ProductSelectorEvent()

}