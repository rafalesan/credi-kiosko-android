package com.rafalesan.credikiosko.onboarding.domain.repository

interface IBusinessRepository {
    suspend fun saveBusiness(businessName: String)
}
