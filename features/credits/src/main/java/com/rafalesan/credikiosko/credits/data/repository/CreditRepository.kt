package com.rafalesan.credikiosko.credits.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.rafalesan.credikiosko.core.commons.data.datasource.local.BusinessLocalDataSource
import com.rafalesan.credikiosko.core.commons.domain.entity.CreditProduct
import com.rafalesan.credikiosko.core.commons.domain.entity.Customer
import com.rafalesan.credikiosko.credits.data.datasource.CreditLocalDataSource
import com.rafalesan.credikiosko.credits.data.mappers.toCreditEntity
import com.rafalesan.credikiosko.credits.data.mappers.toCreditProductEntity
import com.rafalesan.credikiosko.credits.data.mappers.toCreditWithCustomerAndProductsDomain
import com.rafalesan.credikiosko.credits.domain.entity.Credit
import com.rafalesan.credikiosko.credits.domain.entity.CreditWithCustomerAndProducts
import com.rafalesan.credikiosko.credits.domain.repository.ICreditRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CreditRepository(
    private val creditLocalDataSource: CreditLocalDataSource,
    private val businessLocalDataSource: BusinessLocalDataSource
) : ICreditRepository {
    override suspend fun saveCredit(
        credit: Credit,
        customer: Customer,
        creditProducts: List<CreditProduct>
    ) {
        val creditEntity = credit
            .copy(
                businessId = businessLocalDataSource.getBusiness().id,
                customerId = customer.id
            ).toCreditEntity()

        val savedCreditId = creditLocalDataSource.saveCredit(creditEntity)

        val creditProductEntities = creditProducts.map {
            it.copy(creditId = savedCreditId)
                .toCreditProductEntity()
        }

        creditLocalDataSource.saveCreditProducts(creditProductEntities)

    }

    override suspend fun deleteCredit(credit: Credit) {
        creditLocalDataSource.deleteCredit(credit.toCreditEntity())
    }

    override fun getCreditsWithCustomerAndProducts(): Flow<PagingData<CreditWithCustomerAndProducts>> {
        return Pager(
            config = PagingConfig(pageSize = 15, prefetchDistance = 2),
            pagingSourceFactory = {
                creditLocalDataSource.getCreditsWithCustomerAndProducts()
            }
        ).flow
            .map {  pagingData ->
                pagingData.map { it.toCreditWithCustomerAndProductsDomain() }
            }
    }


}
