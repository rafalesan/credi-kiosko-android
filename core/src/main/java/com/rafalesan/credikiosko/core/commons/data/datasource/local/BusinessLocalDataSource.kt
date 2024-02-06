package com.rafalesan.credikiosko.core.commons.data.datasource.local

import com.rafalesan.credikiosko.core.room.dao.BusinessDao
import com.rafalesan.credikiosko.core.room.entity.BusinessEntity
import javax.inject.Inject

class BusinessLocalDataSource @Inject constructor(
    private val businessDao: BusinessDao
) {

    suspend fun saveBusiness(businessName: String) {
        businessDao.insertBusiness(
            BusinessEntity(name = businessName)
        )
    }

    suspend fun getBusiness(): BusinessEntity {
        return businessDao.getBusiness()
    }

    suspend fun existsBusiness(): Boolean {
        return businessDao.existsBusiness()
    }

}
