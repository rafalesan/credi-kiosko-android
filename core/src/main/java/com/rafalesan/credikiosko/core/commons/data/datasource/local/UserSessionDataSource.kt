package com.rafalesan.credikiosko.core.commons.data.datasource.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.rafalesan.credikiosko.core.commons.data.dto.UserSessionData
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class UserSessionDataSource(private val dataStore: DataStore<Preferences>) {

    private object Keys {
        val userSession = stringPreferencesKey("user_session")
    }

    private inline val Preferences.userSession
        get() = this[Keys.userSession]

    suspend fun saveUserSession(userSession: UserSessionData) {
        dataStore.edit {
            val userSessionJsonString = objToJsonString(userSession)
            it[Keys.userSession] = userSessionJsonString
        }
    }

    fun userSession(): Flow<UserSessionData?> {
        return dataStore.data.map {
            val userSessionJsonString = it.userSession ?: return@map null
            val userSession = jsonStringToObj<UserSessionData>(userSessionJsonString)
            userSession
        }.distinctUntilChanged()
    }

    private inline fun <reified T> objToJsonString(obj: T): String {
        val moshi: Moshi = Moshi.Builder().build()
        val jsonAdapter: JsonAdapter<T> = moshi.adapter(T::class.java)
        return jsonAdapter.toJson(obj)
    }

    private inline fun <reified T> jsonStringToObj(jsonString: String): T? {
        val moshi: Moshi = Moshi.Builder().build()
        val jsonAdapter: JsonAdapter<T> = moshi.adapter(T::class.java)
        return jsonAdapter.fromJson(jsonString)
    }

}
