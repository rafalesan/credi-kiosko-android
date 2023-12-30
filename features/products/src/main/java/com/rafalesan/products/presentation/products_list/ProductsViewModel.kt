package com.rafalesan.products.presentation.products_list

import com.rafalesan.credikiosko.core.commons.presentation.base.BaseViewModel
import com.rafalesan.products.domain.entity.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(

) : BaseViewModel() {

    fun perform(productsEvent: ProductsEvent) {
        when (productsEvent) {
            ProductsEvent.CreateNewProduct -> handleCreateNewProductEvent()
            is ProductsEvent.ShowProduct -> handleShowProductEvent(productsEvent.product)
        }
    }

    private fun handleCreateNewProductEvent() {
        toast("Under construction")
    }

    private fun handleShowProductEvent(product: Product) {

    }

}
