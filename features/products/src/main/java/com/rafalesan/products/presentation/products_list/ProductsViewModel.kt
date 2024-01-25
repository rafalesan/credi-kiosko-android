package com.rafalesan.products.presentation.products_list

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rafalesan.credikiosko.core.commons.presentation.base.BaseViewModel
import com.rafalesan.products.domain.entity.Product
import com.rafalesan.products.domain.usecase.GetPagingProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val getPagingProductUseCase: GetPagingProductUseCase
) : BaseViewModel() {

    private val _productList = MutableStateFlow<PagingData<Product>>(PagingData.empty())
    val productList = _productList.asStateFlow()

    private val _action = Channel<ProductsAction>(Channel.BUFFERED)
    val action = _action.receiveAsFlow()

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
        viewModelScope.launch {
            _action.send(ProductsAction.ShowProductForm())
        }
    }

    private fun handleShowProductEvent(product: Product) {
        viewModelScope.launch {
            _action.send(ProductsAction.ShowProductForm(product))
        }
    }

    private fun fetchProducts() {
        viewModelScope.launch {

            getPagingProductUseCase()
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect {
                    _productList.value = it
                }

        }
    }

}
