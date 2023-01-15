package com.rafalesan.credikiosko.core.commons.domain.repository

import com.rafalesan.credikiosko.core.commons.domain.entity.Theme
import com.rafalesan.credikiosko.core.commons.domain.entity.UserSession
import kotlinx.coroutines.flow.Flow

interface IAccountRepository {

    suspend fun saveUserSession(userSession: UserSession)
    fun getUserSessionInfo(): Flow<UserSession?>
    fun getTheme(): Flow<Theme>
    suspend fun changeTheme(lightTheme: Boolean)

}
