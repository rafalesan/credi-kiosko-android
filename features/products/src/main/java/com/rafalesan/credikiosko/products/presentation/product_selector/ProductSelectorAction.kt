package com.rafalesan.credikiosko.products.presentation.product_selector

import com.rafalesan.credikiosko.core.commons.presentation.models.ProductParcelable

sealed class ProductSelectorAction {

    class ReturnProduct(val product: ProductParcelable): ProductSelectorAction()

}
