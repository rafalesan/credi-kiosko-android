package com.rafalesan.credikiosko.onboarding.data.repository

import com.rafalesan.credikiosko.onboarding.data.datasource.BusinessLocalDataSource
import com.rafalesan.credikiosko.onboarding.domain.repository.IBusinessRepository

class BusinessRepository(
    private val businessLocalDataSource: BusinessLocalDataSource
) : IBusinessRepository {

    override suspend fun saveBusiness(businessName: String) {
        businessLocalDataSource.saveBusiness(businessName)
    }

}
