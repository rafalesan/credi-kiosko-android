package com.rafalesan.credikiosko.products.di

import com.rafalesan.credikiosko.products.data.datasource.ProductLocalDataSource
import com.rafalesan.credikiosko.products.data.datasource.ProductRemoteDataSource
import com.rafalesan.credikiosko.products.data.repository.ProductRepository
import com.rafalesan.credikiosko.products.domain.repository.IProductRepository
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
        productRemoteDataSource: ProductRemoteDataSource,
        productLocalDataSource: ProductLocalDataSource
    ): IProductRepository {
        return ProductRepository(
            productRemoteDataSource,
            productLocalDataSource
        )
    }

}
