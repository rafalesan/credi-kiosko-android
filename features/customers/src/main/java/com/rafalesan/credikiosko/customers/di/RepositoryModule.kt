package com.rafalesan.credikiosko.customers.di

import com.rafalesan.credikiosko.customers.data.datasource.CustomerLocalDataSource
import com.rafalesan.credikiosko.customers.data.repository.CustomerRepository
import com.rafalesan.credikiosko.customers.domain.repository.ICustomerRepository
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
    fun provideCustomerRepository(
        customerLocalDataSource: CustomerLocalDataSource
    ): ICustomerRepository {
        return CustomerRepository(customerLocalDataSource)
    }

}
