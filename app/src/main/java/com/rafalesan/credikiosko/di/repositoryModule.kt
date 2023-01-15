package com.rafalesan.credikiosko.di

import com.rafalesan.credikiosko.core.commons.data.repository.AccountRepository
import com.rafalesan.credikiosko.core.commons.data.utils.ApiHandler
import com.rafalesan.credikiosko.core.commons.data.utils.ConnectivityHelper
import com.rafalesan.credikiosko.core.commons.data.utils.IApiHandler
import com.rafalesan.credikiosko.core.commons.data.utils.IConnectivityHelper
import com.rafalesan.credikiosko.core.commons.domain.repository.IAccountRepository
import com.rafalesan.credikiosko.data.auth.repository.AuthRepository
import com.rafalesan.credikiosko.domain.auth.repository.IAuthRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<IConnectivityHelper> { ConnectivityHelper() }
    single<IApiHandler> { ApiHandler(get()) }
    single<IAuthRepository> { AuthRepository(get(), get()) }
    single<IAccountRepository> { AccountRepository(get(), get()) }
}
