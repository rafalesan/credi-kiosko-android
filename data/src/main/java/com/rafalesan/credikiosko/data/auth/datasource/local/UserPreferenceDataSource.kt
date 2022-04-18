package com.rafalesan.credikiosko.data.auth.datasource.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.rafalesan.credikiosko.domain.account.entity.Theme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class UserPreferenceDataSource(private val dataStore: DataStore<Preferences>) {

    private object Keys {
        val theme = booleanPreferencesKey("theme")
    }

    private inline val Preferences.theme
        get() = this[Keys.theme]

    suspend fun saveTheme(lightTheme: Boolean) {
        dataStore.edit {
            it[Keys.theme] = lightTheme
        }
    }

    fun getTheme(): Flow<Theme> {
        return dataStore.data.map {
            when(it.theme) {
                true  -> Theme.NIGHT_YES
                false -> Theme.NIGHT_NO
                else  -> Theme.NIGHT_UNSPECIFIED
            }
        }.distinctUntilChanged()
    }

}
