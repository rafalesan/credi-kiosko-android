package com.rafalesan.credikiosko.credits.data.datasource

import androidx.paging.PagingSource
import com.rafalesan.credikiosko.core.room.dao.CreditDao
import com.rafalesan.credikiosko.core.room.entity.CreditEntity
import com.rafalesan.credikiosko.core.room.entity.CreditProductEntity
import com.rafalesan.credikiosko.core.room.entity.CreditWithCustomerAndProductsEntity
import javax.inject.Inject

class CreditLocalDataSource @Inject constructor(
    private val creditDao: CreditDao
) {

    suspend fun saveCredit(creditEntity: CreditEntity): Long {
        return creditDao.insertCredit(creditEntity)
    }

    suspend fun saveCreditProducts(creditProducts: List<CreditProductEntity>) {
        creditDao.insertCreditProducts(creditProducts)
    }

    suspend fun deleteCredit(creditEntity: CreditEntity) {
        creditDao.deleteCredit(creditEntity)
    }

    fun getCreditsWithCustomerAndProducts(): PagingSource<Int, CreditWithCustomerAndProductsEntity> {
        return creditDao.getCreditsWithCustomerAndProductsPaged()
    }

    suspend fun findCredit(creditId: Long): CreditWithCustomerAndProductsEntity {
        return creditDao.findCredit(creditId)
    }

}
