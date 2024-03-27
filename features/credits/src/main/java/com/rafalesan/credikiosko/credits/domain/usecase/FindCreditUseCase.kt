package com.rafalesan.credikiosko.credits.domain.usecase

import com.rafalesan.credikiosko.credits.domain.entity.CreditWithCustomerAndProducts
import com.rafalesan.credikiosko.credits.domain.repository.ICreditRepository
import javax.inject.Inject

class FindCreditUseCase @Inject constructor(
    private val creditRepository: ICreditRepository
) {

    suspend operator fun invoke(
        creditId: Long
    ): CreditWithCustomerAndProducts {
        return creditRepository.findCredit(creditId)
    }

}
