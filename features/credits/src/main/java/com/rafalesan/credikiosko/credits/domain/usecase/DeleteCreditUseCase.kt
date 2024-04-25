package com.rafalesan.credikiosko.credits.domain.usecase

import com.rafalesan.credikiosko.credits.domain.repository.ICreditRepository
import javax.inject.Inject

class DeleteCreditUseCase @Inject constructor(
    private val creditRepository: ICreditRepository
) {

    suspend operator fun invoke(creditId: Long) {
        creditRepository.deleteCredit(creditId)
    }

}
