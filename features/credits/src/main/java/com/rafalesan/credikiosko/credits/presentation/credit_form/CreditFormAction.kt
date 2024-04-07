package com.rafalesan.credikiosko.credits.presentation.credit_form

import com.rafalesan.credikiosko.core.commons.presentation.models.CreditProductParcelable

sealed class CreditFormAction {
    data object ShowCustomerSelector : CreditFormAction()
    class ShowCreditProductForm(
        val creditProduct: CreditProductParcelable? = null
    ) : CreditFormAction()

    class ShowCreditViewer(val creditId: Long) : CreditFormAction()
}
