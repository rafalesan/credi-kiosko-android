package com.rafalesan.credikiosko.core.commons.data.repository

import com.rafalesan.credikiosko.core.commons.data.datasource.local.BusinessLocalDataSource
import com.rafalesan.credikiosko.core.commons.data.mappers.toDomain
import com.rafalesan.credikiosko.core.commons.domain.entity.Business
import com.rafalesan.credikiosko.core.commons.domain.repository.IBusinessRepository

class BusinessRepository(
    private val businessLocalDataSource: BusinessLocalDataSource
) : IBusinessRepository {

    override suspend fun saveBusiness(businessName: String) {
        businessLocalDataSource.saveBusiness(businessName)
    }

    override suspend fun getBusiness(): Business {
        val businessEntity = businessLocalDataSource.getBusiness()
        return businessEntity.toDomain()
    }

    override suspend fun existsBusiness(): Boolean {
        return businessLocalDataSource.existsBusiness()
    }

    override suspend fun updateBusinessName(businessName: String) {
        businessLocalDataSource.updateBusinessName(businessName)
    }

}
