package com.rafalesan.credikiosko.di

import androidx.datastore.preferences.preferencesDataStore
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataStoreModule = module {
    single(named(USER_SESSION_PREFERENCES)) { preferencesDataStore(name = USER_SESSION_PREFERENCES).getValue(get(), String::javaClass) }
}

const val USER_SESSION_PREFERENCES = "userSessionPreferences"