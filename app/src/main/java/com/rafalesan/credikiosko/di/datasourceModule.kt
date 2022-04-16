package com.rafalesan.credikiosko.di

import com.rafalesan.credikiosko.data.auth.datasource.AuthDataSource
import org.koin.dsl.module

val datasourceModule = module {
    single { AuthDataSource(get()) }
}