package com.rafalesan.credikiosko.credits.domain.usecase

import com.rafalesan.credikiosko.core.commons.domain.entity.CreditProduct
import com.rafalesan.credikiosko.core.commons.domain.utils.ResultOf
import com.rafalesan.credikiosko.credits.domain.entity.Credit
import com.rafalesan.credikiosko.credits.domain.repository.ICreditRepository
import com.rafalesan.credikiosko.credits.domain.validator.CreditInputsValidator
import javax.inject.Inject

class SaveCreditUseCase @Inject constructor (
    private val creditRepository: ICreditRepository
) {

    suspend operator fun invoke(
        credit: Credit,
        creditProducts: List<CreditProduct>
    ) : ResultOf<Long, CreditInputsValidator.CreditInputValidation> {

        val validations = CreditInputsValidator.validateCreditInputs(
            credit.customerId,
            creditProducts
        )

        if (validations.isNotEmpty()) {
            return ResultOf.Failure.InvalidData(validations)
        }

        val creditIdSaved = creditRepository.saveCredit(
            credit,
            creditProducts
        )

        return ResultOf.Success(creditIdSaved)

    }

}
