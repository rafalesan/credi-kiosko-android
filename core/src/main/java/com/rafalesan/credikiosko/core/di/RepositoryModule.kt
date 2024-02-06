package com.rafalesan.credikiosko.core.di

import com.rafalesan.credikiosko.core.commons.data.datasource.local.BusinessLocalDataSource
import com.rafalesan.credikiosko.core.commons.data.repository.BusinessRepository
import com.rafalesan.credikiosko.core.commons.domain.repository.IBusinessRepository
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