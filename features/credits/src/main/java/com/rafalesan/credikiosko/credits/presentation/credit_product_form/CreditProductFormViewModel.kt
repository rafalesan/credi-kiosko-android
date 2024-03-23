package com.rafalesan.credikiosko.credits.presentation.credit_product_form

import androidx.lifecycle.viewModelScope
import com.rafalesan.credikiosko.core.commons.presentation.base.BaseViewModel
import com.rafalesan.credikiosko.core.commons.presentation.mappers.toProductDomain
import com.rafalesan.credikiosko.core.commons.presentation.models.ProductParcelable
import com.rafalesan.credikiosko.core.commons.zeroLong
import com.rafalesan.credikiosko.credits.domain.usecase.CalculateProductLineTotalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreditProductFormViewModel @Inject constructor(
    private val calculateProductLineTotalUseCase: CalculateProductLineTotalUseCase
) : BaseViewModel() {

    private val _viewState = MutableStateFlow(CreditProductFormViewState())
    val viewState = _viewState.asStateFlow()

    private val _action = Channel<CreditProductFormAction>(Channel.BUFFERED)
    val action = _action.receiveAsFlow()

    fun perform(event: CreditProductFormEvent) {
        when (event) {
            CreditProductFormEvent.ProductSelectorPressed -> handleProductSelectorPressed()
            is CreditProductFormEvent.SetProduct -> handleSetProductEvent(event.product)
            is CreditProductFormEvent.ProductPriceChanged -> handleProductPriceChangedEvent(event.productPrice)
            is CreditProductFormEvent.ProductsQuantityChanged -> handleProductsQuantityChangedEvent(event.productsQuantity)
        }
    }

    private fun handleProductsQuantityChangedEvent(productsQuantity: String) {
        _viewState.update {
            it.copy(
                productsQuantity = productsQuantity
            )
        }
        performTotalCalculation()
    }

    private fun handleProductPriceChangedEvent(productPrice: String) {
        _viewState.update {
            it.copy(
                productPrice = productPrice
            )
        }
        performTotalCalculation()
    }

    private fun handleSetProductEvent(product: ProductParcelable) {
        if (product.id == zeroLong) {
            return
        }
        val productDomain = product.toProductDomain()
        _viewState.update {
            it.copy(
                productSelected = productDomain,
                productPrice = productDomain.price
            )
        }
        performTotalCalculation()
    }

    private fun handleProductSelectorPressed() {
        viewModelScope.launch {
            _action.send(CreditProductFormAction.ShowProductSelector)
        }
    }

    private fun performTotalCalculation() {
        val productPrice = viewState.value.productPrice
        val productsQuantity = viewState.value.productsQuantity
        val total = calculateProductLineTotalUseCase(
            productPrice,
            productsQuantity
        )
        _viewState.update {
            it.copy(totalAmount = total)
        }
    }

}
