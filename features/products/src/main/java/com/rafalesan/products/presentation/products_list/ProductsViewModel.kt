package com.rafalesan.products.presentation.products_list

import androidx.lifecycle.viewModelScope
import com.rafalesan.credikiosko.core.commons.domain.utils.ResultOf
import com.rafalesan.credikiosko.core.commons.presentation.base.BaseViewModel
import com.rafalesan.products.domain.entity.Product
import com.rafalesan.products.domain.usecase.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase
) : BaseViewModel() {

    private val _productList = MutableStateFlow<List<Product>>(listOf())
    val productList = _productList.asStateFlow()

    init {
        fetchProducts()
    }

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
        toast("Show ${product.name} under construction")
    }

    private fun fetchProducts() {
        viewModelScope.launch {
            //TODO: ADD LOADING
            val result = getProductsUseCase.invoke()
            when (result) {
                is ResultOf.Success -> handleSuccessFetchProduct(result)
                is ResultOf.Failure -> handleFailure(result)
                is ResultOf.InvalidData -> TODO()
            }
        }
    }

    private fun handleSuccessFetchProduct(result: ResultOf.Success<List<Product>>) {
        _productList.value = result.value
    }

    private fun handleFailure(resultFailure: ResultOf.Failure) {
        when (resultFailure) {
            is ResultOf.Failure.ApiFailure -> {
                Timber.e(resultFailure.message)
            }
            ResultOf.Failure.ApiNotAvailable -> {
                Timber.e("ApiNotAvailable")
            }
            ResultOf.Failure.NoInternet -> {
                Timber.e("NoInternet")
            }
            ResultOf.Failure.UnknownFailure -> {
                Timber.e("UnknownFailure")
            }
        }
    }

}
