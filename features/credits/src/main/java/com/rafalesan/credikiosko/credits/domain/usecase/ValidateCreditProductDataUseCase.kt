package com.rafalesan.credikiosko.credits.domain.usecase

import com.rafalesan.credikiosko.core.commons.domain.entity.CreditProduct
import com.rafalesan.credikiosko.core.commons.domain.utils.ResultOf
import com.rafalesan.credikiosko.credits.domain.validator.CreditProductInputsValidator
import javax.inject.Inject

class ValidateCreditProductDataUseCase @Inject constructor() {

    operator fun invoke(
        creditProduct: CreditProduct
    ): ResultOf<Unit, CreditProductInputsValidator.CreditProductInputValidation> {

        val validations = CreditProductInputsValidator.validateCreditProductInputs(
            creditProduct.productId,
            creditProduct.productPrice,
            creditProduct.quantity
        )

        if (validations.isNotEmpty()) {
            return ResultOf.Failure.InvalidData(validations)
        }

        return ResultOf.Success(Unit)

    }

}
