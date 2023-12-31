package com.rafalesan.products.di

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
    fun provideProductRepository(): IProductRepository {
        return ProductRepository()
    }

}
