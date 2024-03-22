package com.rafalesan.credikiosko.credits.presentation.credit_form

import com.rafalesan.credikiosko.credits.domain.entity.CreditProduct

sealed class CreditFormEvent {

    data object CustomerSelectorPressed: CreditFormEvent()
    data object CreateCredit: CreditFormEvent()
    class DeleteProductLine(val creditProduct: CreditProduct): CreditFormEvent()
    class EditProductLine(val creditProduct: CreditProduct): CreditFormEvent()
    data object AddProductLine : CreditFormEvent()

}
