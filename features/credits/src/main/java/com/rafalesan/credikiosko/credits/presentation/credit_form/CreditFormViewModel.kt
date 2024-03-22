package com.rafalesan.credikiosko.credits.presentation.credit_form

import com.rafalesan.credikiosko.core.commons.presentation.base.BaseViewModel
import com.rafalesan.credikiosko.credits.domain.entity.CreditProduct
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CreditFormViewModel @Inject constructor(

) : BaseViewModel() {
    private val _viewState = MutableStateFlow(CreditFormState())

    val viewState = _viewState.asStateFlow()
    fun perform(event: CreditFormEvent) {
        when (event) {
            CreditFormEvent.CustomerSelectorPressed -> handleCustomerSelectorPressed()
            CreditFormEvent.CreateCredit -> handleCreateCreditEvent()
            CreditFormEvent.AddProductLine -> handleAddProductLineEvent()
            is CreditFormEvent.DeleteProductLine -> handleDeleteProductLineEvent(event.creditProduct)
            is CreditFormEvent.EditProductLine -> handleEditProductLineEvent(event.creditProduct)
        }
    }

    private fun handleEditProductLineEvent(creditProduct: CreditProduct) {
        toast("En Construcción (${creditProduct.productId})")
    }

    private fun handleDeleteProductLineEvent(creditProduct: CreditProduct) {
        toast("En Construcción (${creditProduct.productId})")
    }

    private fun handleCustomerSelectorPressed() {
        toast("En Construcción")
    }

    private fun handleCreateCreditEvent() {
        toast("En Construcción")
    }

    private fun handleAddProductLineEvent() {
        toast("En Contrucción")
    }

}