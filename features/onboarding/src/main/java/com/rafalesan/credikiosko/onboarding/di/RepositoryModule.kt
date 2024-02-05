package com.rafalesan.credikiosko.onboarding.di

import com.rafalesan.credikiosko.onboarding.data.datasource.BusinessLocalDataSource
import com.rafalesan.credikiosko.onboarding.data.repository.BusinessRepository
import com.rafalesan.credikiosko.onboarding.domain.repository.IBusinessRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideProductRepository(
        businessLocalDataSource: BusinessLocalDataSource
    ): IBusinessRepository {
        return BusinessRepository(businessLocalDataSource)
    }

}