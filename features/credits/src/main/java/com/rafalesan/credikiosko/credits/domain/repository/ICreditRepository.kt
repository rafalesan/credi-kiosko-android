package com.rafalesan.credikiosko.credits.domain.repository

import androidx.paging.PagingData
import com.rafalesan.credikiosko.core.commons.domain.entity.CreditProduct
import com.rafalesan.credikiosko.credits.domain.entity.Credit
import com.rafalesan.credikiosko.credits.domain.entity.CreditWithCustomerAndProducts
import kotlinx.coroutines.flow.Flow

interface ICreditRepository {

    suspend fun saveCredit(
        credit: Credit,
        creditProducts: List<CreditProduct>
    )

    suspend fun deleteCredit(credit: Credit)

    fun getCreditsWithCustomerAndProducts(): Flow<PagingData<CreditWithCustomerAndProducts>>
}
