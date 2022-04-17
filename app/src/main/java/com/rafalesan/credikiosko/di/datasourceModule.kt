package com.rafalesan.credikiosko.di

import com.rafalesan.credikiosko.data.auth.datasource.local.UserSessionDataSource
import com.rafalesan.credikiosko.data.auth.datasource.remote.AuthDataSource
import org.koin.core.qualifier.named
import org.koin.dsl.module

val datasourceModule = module {
    single { AuthDataSource(get()) }
    single { UserSessionDataSource(get(named(USER_SESSION_PREFERENCES))) }
}