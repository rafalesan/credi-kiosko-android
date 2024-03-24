package com.rafalesan.credikiosko.credits.presentation.credit_form

import com.rafalesan.credikiosko.core.commons.domain.entity.CreditProduct
import com.rafalesan.credikiosko.core.commons.presentation.models.CreditProductParcelable
import com.rafalesan.credikiosko.core.commons.presentation.models.CustomerParcelable

sealed class CreditFormEvent {

    data object CustomerSelectorPressed: CreditFormEvent()
    data object CreateCredit: CreditFormEvent()
    class DeleteProductLine(val creditProduct: CreditProduct): CreditFormEvent()
    class EditProductLine(val creditProduct: CreditProduct): CreditFormEvent()
    data object AddProductLine : CreditFormEvent()
    class SetCustomer(val customer: CustomerParcelable): CreditFormEvent()
    class AddCreditProduct(val creditProduct: CreditProductParcelable): CreditFormEvent()
}
