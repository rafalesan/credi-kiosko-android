package com.rafalesan.credikiosko.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.rafalesan.credikiosko.ApplicationBuildConfigFieldsProvider
import com.rafalesan.credikiosko.core.auth.data.remote.AuthDataSource
import com.rafalesan.credikiosko.core.auth.data.remote.IAuthApi
import com.rafalesan.credikiosko.core.commons.data.build_config_provider.BuildConfigFieldsProvider
import com.rafalesan.credikiosko.core.commons.data.datasource.local.UserPreferenceDataSource
import com.rafalesan.credikiosko.core.commons.data.datasource.local.UserSessionDataSource
import com.rafalesan.credikiosko.core.commons.data.utils.IApiHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {

    @Provides
    fun provideBuildConfigFieldsProvider() : BuildConfigFieldsProvider {
        return ApplicationBuildConfigFieldsProvider()
    }

    @Provides
    @Singleton
    fun provideAuthDataSource(authApi: IAuthApi,
                              apiHandler: IApiHandler) : AuthDataSource {
        return AuthDataSource(authApi, apiHandler)
    }

    @Provides
    @Singleton
    fun provideUserSessionDataSource(
        @Named(DataStoreModule.USER_SESSION_PREFERENCES) preferences: DataStore<Preferences>
    ): UserSessionDataSource {
        return UserSessionDataSource(preferences)
    }

    @Provides
    @Singleton
    fun provideUserPreferences(
        @Named(DataStoreModule.USER_PREFERENCES) preferences: DataStore<Preferences>
    ) : UserPreferenceDataSource {
        return UserPreferenceDataSource(preferences)
    }

}
