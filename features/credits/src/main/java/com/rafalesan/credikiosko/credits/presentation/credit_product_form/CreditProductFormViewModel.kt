package com.rafalesan.credikiosko.credits.presentation.credit_product_form

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.rafalesan.credikiosko.core.commons.creditProductNavKey
import com.rafalesan.credikiosko.core.commons.domain.entity.CreditProduct
import com.rafalesan.credikiosko.core.commons.domain.entity.Product
import com.rafalesan.credikiosko.core.commons.domain.utils.ResultOf
import com.rafalesan.credikiosko.core.commons.emptyString
import com.rafalesan.credikiosko.core.commons.presentation.base.BaseViewModel
import com.rafalesan.credikiosko.core.commons.presentation.mappers.toProductDomain
import com.rafalesan.credikiosko.core.commons.presentation.models.CreditProductParcelable
import com.rafalesan.credikiosko.core.commons.presentation.models.ProductParcelable
import com.rafalesan.credikiosko.core.commons.zeroLong
import com.rafalesan.credikiosko.credits.domain.usecase.CalculateProductLineTotalUseCase
import com.rafalesan.credikiosko.credits.domain.usecase.ValidateCreditProductDataUseCase
import com.rafalesan.credikiosko.credits.domain.validator.CreditProductInputsValidator
import com.rafalesan.credikiosko.credits.domain.validator.CreditProductInputsValidator.CreditProductInputValidation.*
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
class CreditProductFormViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val calculateProductLineTotalUseCase: CalculateProductLineTotalUseCase,
    private val validateCreditProductDataUseCase: ValidateCreditProductDataUseCase
) : BaseViewModel() {

    private val _viewState = MutableStateFlow(CreditProductFormViewState())
    val viewState = _viewState.asStateFlow()

    private val _action = Channel<CreditProductFormAction>(Channel.BUFFERED)
    val action = _action.receiveAsFlow()

    init {
        val creditProductFromNav = savedStateHandle.get<CreditProductParcelable>(creditProductNavKey)

        creditProductFromNav?.let { creditProduct ->
            initViewStateWith(creditProduct)
        }

    }

    fun perform(event: CreditProductFormEvent) {
        when (event) {
            CreditProductFormEvent.ProductSelectorPressed -> handleProductSelectorPressed()
            is CreditProductFormEvent.SetProduct -> handleSetProductEvent(event.product)
            is CreditProductFormEvent.ProductPriceChanged -> handleProductPriceChangedEvent(event.productPrice)
            is CreditProductFormEvent.ProductsQuantityChanged -> handleProductsQuantityChangedEvent(event.productsQuantity)
            CreditProductFormEvent.AddCreditProductLine -> handleAddCreditProductLine()
        }
    }

    private fun handleAddCreditProductLine() {

        clearInputValidations()

        val result = validateCreditProductDataUseCase(buildCreditProductFromViewState())

        when (result) {
            is ResultOf.Success -> returnPreviousScreenWithResult()
            is ResultOf.Failure.InvalidData -> handleInvalidData(result)
            else -> { Timber.e("Operation not supported: $result") }
        }

    }

    private fun handleInvalidData(
        result: ResultOf.Failure.InvalidData<CreditProductInputsValidator.CreditProductInputValidation>
    ) {

        result.validations.forEach { validation ->
            when (validation) {
                EMPTY_PRODUCT -> _viewState.update {
                    it.copy(productSelectedError = validation.errorResId)
                }
                EMPTY_PRODUCT_PRICE -> _viewState.update {
                    it.copy(productPriceError = validation.errorResId)
                }
                EMPTY_PRODUCTS_QUANTITY -> _viewState.update {
                    it.copy(productsQuantityError = validation.errorResId)
                }
                INVALID_PRODUCT_PRICE_IS_ZERO -> _viewState.update {
                    it.copy(productPriceError = validation.errorResId)
                }
                INVALID_PRODUCTS_QUANTITY_IS_ZERO -> _viewState.update {
                    it.copy(productsQuantityError = validation.errorResId)
                }
            }
        }
    }

    private fun clearInputValidations() {
        _viewState.update {
            it.copy(
                productSelectedError = null,
                productPriceError = null,
                productsQuantityError = null
            )
        }
    }

    private fun returnPreviousScreenWithResult() {
        viewModelScope.launch {
            val creditProduct = buildCreditProductParcelableFromViewState()
            _action.send(CreditProductFormAction.ReturnCreditProductLine(creditProduct))
        }
    }

    private fun handleProductsQuantityChangedEvent(productsQuantity: String) {
        _viewState.update {
            it.copy(
                productsQuantity = productsQuantity,
                productsQuantityError = null
            )
        }
        performTotalCalculation()
    }

    private fun handleProductPriceChangedEvent(productPrice: String) {
        _viewState.update {
            it.copy(
                productPrice = productPrice,
                productPriceError = null
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
                productPrice = productDomain.price,
                productSelectedError = null,
                productPriceError = null
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

    private fun initViewStateWith(creditProduct: CreditProductParcelable) {
        _viewState.update {

            val productSelected = Product(
                id = creditProduct.productId,
                name = creditProduct.productName,
                price = creditProduct.productPrice
            )

            it.copy(
                productSelected = productSelected,
                creditProductId = creditProduct.id,
                productPrice = creditProduct.productPrice,
                productsQuantity = creditProduct.quantity,
                totalAmount = creditProduct.total
            )
        }
    }

    private fun buildCreditProductParcelableFromViewState(): CreditProductParcelable {
        return with(viewState.value) {
            CreditProductParcelable(
                id = creditProductId ?: zeroLong,
                productId = productSelected!!.id,
                productName = productSelected.name,
                productPrice = productPrice!!,
                quantity = productsQuantity,
                total = totalAmount
            )
        }
    }

    private fun buildCreditProductFromViewState(): CreditProduct {
        return with(viewState.value) {
            CreditProduct(
                id = creditProductId ?: zeroLong,
                productId = productSelected?.id ?: zeroLong,
                productName = productSelected?.name ?: emptyString,
                productPrice = productPrice ?: emptyString,
                quantity = productsQuantity,
                total = totalAmount
            )
        }
    }

}
