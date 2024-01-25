package com.rafalesan.products.presentation.product_form

import androidx.lifecycle.SavedStateHandle
import com.rafalesan.credikiosko.core.commons.emptyString
import com.rafalesan.credikiosko.core.commons.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ProductFormViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val _viewState = MutableStateFlow(ProductFormState())
    val viewState = _viewState.asStateFlow()

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

            ProductFormEvent.SaveProduct -> {
                toast("Under construction")
            }
        }
    }

}
