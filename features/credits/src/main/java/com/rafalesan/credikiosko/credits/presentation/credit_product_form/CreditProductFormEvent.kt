package com.rafalesan.credikiosko.credits.presentation.credit_product_form

import com.rafalesan.credikiosko.core.commons.presentation.models.ProductParcelable

sealed class CreditProductFormEvent {
    data object ProductSelectorPressed: CreditProductFormEvent()
    class ProductPriceChanged(val productPrice: String): CreditProductFormEvent()
    class ProductsQuantityChanged(val productsQuantity: String): CreditProductFormEvent()
    class SetProduct(val product: ProductParcelable): CreditProductFormEvent()
}
