package com.rafalesan.credikiosko.credits.domain.usecase

import com.rafalesan.credikiosko.core.commons.domain.entity.CreditProduct
import com.rafalesan.credikiosko.credits.domain.entity.Credit
import com.rafalesan.credikiosko.credits.domain.repository.ICreditRepository
import javax.inject.Inject

class SaveCreditUseCase @Inject constructor (
    private val creditRepository: ICreditRepository
) {

    suspend operator fun invoke(
        credit: Credit,
        creditProducts: List<CreditProduct>
    ) {

        creditRepository.saveCredit(
            credit,
            creditProducts
        )

    }

}
