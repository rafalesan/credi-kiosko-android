package com.rafalesan.credikiosko.onboarding.data.datasource

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

}
