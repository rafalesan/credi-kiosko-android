package com.rafalesan.credikiosko.credits.presentation.credit_product_form

import com.rafalesan.credikiosko.core.commons.presentation.models.CreditProductParcelable

sealed class CreditProductFormAction {
    data object ShowProductSelector : CreditProductFormAction()
    class ReturnCreditProductLine(val creditProduct: CreditProductParcelable) : CreditProductFormAction()
}
