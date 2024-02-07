package com.rafalesan.products.di

import com.rafalesan.products.data.datasource.ProductLocalDataSource
import com.rafalesan.products.data.datasource.ProductRemoteDataSource
import com.rafalesan.products.data.repository.ProductRepository
import com.rafalesan.products.domain.repository.IProductRepository
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
