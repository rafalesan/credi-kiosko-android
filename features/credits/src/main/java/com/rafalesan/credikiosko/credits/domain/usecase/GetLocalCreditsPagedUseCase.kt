package com.rafalesan.credikiosko.credits.domain.usecase

import androidx.paging.PagingData
import com.rafalesan.credikiosko.credits.domain.entity.CreditWithCustomerAndProducts
import com.rafalesan.credikiosko.credits.domain.repository.ICreditRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocalCreditsPagedUseCase @Inject constructor(
    private val creditRepository: ICreditRepository
) {

    operator fun invoke(): Flow<PagingData<CreditWithCustomerAndProducts>> {
        return creditRepository.getCreditsWithCustomerAndProducts()
    }

}
