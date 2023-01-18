package com.rafalesan.credikiosko.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {

    companion object {
        const val USER_SESSION_PREFERENCES = "userSessionPreferences"
        const val USER_PREFERENCES = "userPreferences"
    }

    @Provides
    @Singleton
    @Named(USER_SESSION_PREFERENCES)
    fun provideUserSessionPreferences(@ApplicationContext appContext: Context) : DataStore<Preferences> {
        return preferencesDataStore(name = USER_SESSION_PREFERENCES).getValue(appContext, String::javaClass)
    }

    @Provides
    @Singleton
    @Named(USER_PREFERENCES)
    fun provideUserPreferences(@ApplicationContext appContext: Context) : DataStore<Preferences> {
        return preferencesDataStore(name = USER_PREFERENCES).getValue(appContext, String::javaClass)
    }

}
