package com.rafalesan.credikiosko.credits.presentation.credit_form

sealed class CreditFormAction {
    data object ShowCustomerSelector : CreditFormAction()
    data object ShowCreditProductForm : CreditFormAction()
}
