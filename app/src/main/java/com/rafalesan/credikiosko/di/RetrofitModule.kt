package com.rafalesan.credikiosko.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.rafalesan.credikiosko.BuildConfig
import com.rafalesan.credikiosko.core.commons.data.datasource.local.UserSessionDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    companion object {
        private const val REQUEST_TIME_OUT: Long = 60
    }

    @Provides
    @Singleton
    fun provideAuthorizationHeader(userSessionDataSource: UserSessionDataSource): Interceptor {
        val userSessionData = userSessionDataSource.userSession()
        val token = runBlocking { userSessionData.first() }?.token
        return Interceptor { chain ->
            chain.proceed(
                chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer ${token ?: ""}")
                        .build()
                         )
        }
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }

    @Provides
    @Singleton
    fun provideChuckerInterceptor(@ApplicationContext AppContext: Context): ChuckerInterceptor {
        return ChuckerInterceptor.Builder(AppContext).build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(tokenInterceptor: Interceptor,
                                    logging: HttpLoggingInterceptor,
                                    chuckerInterceptor: ChuckerInterceptor): OkHttpClient {

        val okHttpBuilder = OkHttpClient.Builder()
                .readTimeout(REQUEST_TIME_OUT, TimeUnit.MINUTES)
                .connectTimeout(REQUEST_TIME_OUT, TimeUnit.MINUTES)
                .addInterceptor(tokenInterceptor)

        if(BuildConfig.DEBUG) {
            okHttpBuilder
                .addInterceptor(logging)
                .addInterceptor(chuckerInterceptor)
        }

        return okHttpBuilder.build()

    }

    @Provides
    @Singleton
    fun provideMoshiConverter(): MoshiConverterFactory {
        return MoshiConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        moshiConverterFactory: MoshiConverterFactory,
        okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(moshiConverterFactory)
            .baseUrl(BuildConfig.API_BASE_URL)
            .build()

}
