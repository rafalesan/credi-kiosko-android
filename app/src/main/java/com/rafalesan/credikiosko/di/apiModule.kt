package com.rafalesan.credikiosko.di

import com.rafalesan.credikiosko.data.auth.datasource.remote.IAuthApi
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    single<IAuthApi> { createApi(get()) }
}

private inline fun <reified T> createApi(retrofit: Retrofit): T {
    return retrofit.create(T::class.java)
}