package com.rafalesan.credikiosko.di

import com.rafalesan.credikiosko.data.auth.datasource.remote.IAuthApi
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
    fun provideAuthApi(retrofit: Retrofit) : IAuthApi {
        return retrofit.create(IAuthApi::class.java)
    }

}
