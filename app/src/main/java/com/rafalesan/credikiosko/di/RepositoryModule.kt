package com.rafalesan.credikiosko.di

import com.rafalesan.credikiosko.core.auth.data.repository.AuthRepository
import com.rafalesan.credikiosko.core.auth.domain.auth.repository.IAuthRepository
import com.rafalesan.credikiosko.core.commons.data.datasource.local.UserPreferenceDataSource
import com.rafalesan.credikiosko.core.commons.data.datasource.local.UserSessionDataSource
import com.rafalesan.credikiosko.core.commons.data.repository.SessionRepository
import com.rafalesan.credikiosko.core.commons.data.utils.ApiHandler
import com.rafalesan.credikiosko.core.commons.data.utils.ConnectivityHelper
import com.rafalesan.credikiosko.core.commons.data.utils.IApiHandler
import com.rafalesan.credikiosko.core.commons.data.utils.IConnectivityHelper
import com.rafalesan.credikiosko.core.commons.domain.repository.ISessionRepository
import com.rafalesan.credikiosko.data.auth.datasource.remote.AuthDataSource
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
    fun provideConnectivityHelper() : IConnectivityHelper {
        return ConnectivityHelper()
    }

    @Provides
    @Singleton
    fun provideApiHandler(connectivityHelper: IConnectivityHelper) : IApiHandler {
        return ApiHandler(connectivityHelper)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        authDataSource: AuthDataSource
    ) : IAuthRepository {
        return AuthRepository(authDataSource)
    }

    //TODO: Instead AccountRepository, this should be named as SessionRepository
    @Provides
    @Singleton
    fun provideSessionRepository(
        userSessionDataSource: UserSessionDataSource,
        userPreferenceDataSource: UserPreferenceDataSource
    ) : ISessionRepository {
        return SessionRepository(userSessionDataSource, userPreferenceDataSource)
    }

}
