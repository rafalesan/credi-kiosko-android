package com.rafalesan.credikiosko.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.rafalesan.credikiosko.BuildConfig
import com.rafalesan.credikiosko.data.auth.datasource.local.UserSessionDataSource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

const val REQUEST_TIME_OUT: Long = 60

val retrofitModule = module {

    single { provideAuthorizationHeader(get()) }
    single { provideLoggingInterceptor() }
    single { provideChuckerInterceptor(get()) }
    single { provideOkHttpClient(get(), get(), get()) }
    single { provideMoshiConverter() }
    single { provideRetrofit(get(), get()) }

}

private fun provideAuthorizationHeader(userSessionDataSource: UserSessionDataSource): Interceptor {
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

private fun provideLoggingInterceptor(): HttpLoggingInterceptor {
    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY
    return logging
}

private fun provideChuckerInterceptor(AppContext: Context): ChuckerInterceptor {
    return ChuckerInterceptor.Builder(AppContext).build()
}

private fun provideOkHttpClient(tokenInterceptor: Interceptor,
                                logging: HttpLoggingInterceptor,
                                chuckerInterceptor: ChuckerInterceptor): OkHttpClient {

    val okHttpBuilder = OkHttpClient.Builder()
            .readTimeout(REQUEST_TIME_OUT, TimeUnit.MINUTES)
            .connectTimeout(REQUEST_TIME_OUT, TimeUnit.MINUTES)
            .addInterceptor(tokenInterceptor)

    if(BuildConfig.DEBUG) {
        okHttpBuilder.addNetworkInterceptor(logging)
                .addInterceptor(chuckerInterceptor)
    }

    return okHttpBuilder.build()

}

private fun provideMoshiConverter(): MoshiConverterFactory {
    return MoshiConverterFactory.create()
}

private fun provideRetrofit(moshiConverterFactory: MoshiConverterFactory,
                            okHttpClient: OkHttpClient) = Retrofit.Builder()
        .client(okHttpClient)
        .addConverterFactory(moshiConverterFactory)
        .baseUrl(BuildConfig.API_BASE_URL)
        .build()