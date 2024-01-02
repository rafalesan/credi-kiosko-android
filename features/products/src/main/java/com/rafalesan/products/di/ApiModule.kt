package com.rafalesan.products.di

import com.rafalesan.products.data.datasource.IProductApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    @Singleton
    fun provideProductApi(retrofit: Retrofit): IProductApi {
        return retrofit.create(IProductApi::class.java)
    }

}
