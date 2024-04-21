package com.rafalesan.credikiosko.core.commons.domain.repository

import com.rafalesan.credikiosko.core.commons.domain.entity.Business

interface IBusinessRepository {
    suspend fun saveBusiness(businessName: String)
    suspend fun getBusiness(): Business
    suspend fun existsBusiness(): Boolean
    suspend fun updateBusinessName(businessName: String)
}
