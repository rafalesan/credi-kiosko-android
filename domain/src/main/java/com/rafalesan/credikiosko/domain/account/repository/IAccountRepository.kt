package com.rafalesan.credikiosko.domain.account.repository

import com.rafalesan.credikiosko.domain.account.entity.Theme
import com.rafalesan.credikiosko.domain.auth.entity.UserSession
import kotlinx.coroutines.flow.Flow

interface IAccountRepository {

    suspend fun saveUserSession(userSession: UserSession)
    fun getTheme(): Flow<Theme>
    suspend fun changeTheme(lightTheme: Boolean)

}
