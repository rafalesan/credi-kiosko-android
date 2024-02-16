package com.rafalesan.products.presentation.product_form

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.rafalesan.credikiosko.core.commons.domain.utils.ResultOf
import com.rafalesan.credikiosko.core.commons.emptyString
import com.rafalesan.credikiosko.core.commons.presentation.base.BaseViewModel
import com.rafalesan.credikiosko.core.commons.zeroLong
import com.rafalesan.products.domain.entity.Product
import com.rafalesan.products.domain.usecase.DeleteProductUseCase
import com.rafalesan.products.domain.usecase.SaveProductUseCase
import com.rafalesan.products.domain.validator.ProductValidator
import com.rafalesan.products.domain.validator.ProductValidator.ProductInputValidation.EMPTY_PRODUCT_NAME
import com.rafalesan.products.domain.validator.ProductValidator.ProductInputValidation.EMPTY_PRODUCT_PRICE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProductFormViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val saveProductUseCase: SaveProductUseCase,
    private val deleteProductUseCase: DeleteProductUseCase
) : BaseViewModel() {

    private val _viewState = MutableStateFlow(ProductFormState())
    val viewState = _viewState.asStateFlow()

    private val _action = Channel<ProductFormAction>(Channel.BUFFERED)
    val action = _action.receiveAsFlow()

    init {
        val productId = savedStateHandle.get<String?>("product_id")
        val productName = savedStateHandle.get<String?>("product_name")
        val productPrice = savedStateHandle.get<String?>("product_price")
        _viewState.update {
            it.copy(
                productId = productId?.toLong(),
                productName = productName ?: emptyString,
                productPrice = productPrice ?: emptyString,
                isNewProduct = productId == null
            )
        }
    }

    fun perform(event: ProductFormEvent) {
        when (event) {
            is ProductFormEvent.SetProductName -> _viewState.update {
                it.copy(productName = event.productName)
            }
            is ProductFormEvent.SetProductPrice -> _viewState.update {
                it.copy(productPrice = event.productPrice)
            }

            ProductFormEvent.SaveProduct -> saveProduct()
            ProductFormEvent.DeleteProduct -> deleteProduct()
        }
    }

    private fun deleteProduct() {
        viewModelScope.launch {
            val product = buildProductFromViewState()
            deleteProductUseCase(product)
            goBackToProductList()
        }
    }

    private fun saveProduct() {

        viewModelScope.launch {
            val product = buildProductFromViewState()
            val result = saveProductUseCase(product)

            when (result) {
                is ResultOf.Success -> goBackToProductList()
                is ResultOf.Failure.InvalidData -> handleInvalidData(result)
                else -> { Timber.e("Operation not supported: $result") }
            }

        }

    }

    private fun goBackToProductList() {
        viewModelScope.launch {
            _action.send(ProductFormAction.ReturnToProductList)
        }
    }

    private fun handleInvalidData(
        result: ResultOf.Failure.InvalidData<ProductValidator.ProductInputValidation>
    ) {
        result.validations.forEach { validation ->
            when (validation) {
                EMPTY_PRODUCT_NAME -> _viewState.update {
                    it.copy(productNameError = validation.errorResId)
                }
                EMPTY_PRODUCT_PRICE -> _viewState.update {
                    it.copy(productPriceError = validation.errorResId)
                }
            }
        }
    }

    private fun buildProductFromViewState(): Product {
        return with(viewState.value) {
            Product(
                productId ?: zeroLong,
                productName,
                productPrice
            )
        }
    }

}
