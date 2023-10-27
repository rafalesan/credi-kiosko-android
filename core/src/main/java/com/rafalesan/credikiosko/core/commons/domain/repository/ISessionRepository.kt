package com.rafalesan.credikiosko.core.commons.domain.repository

import com.rafalesan.credikiosko.core.commons.domain.entity.Theme
import com.rafalesan.credikiosko.core.commons.domain.entity.UserSession
import kotlinx.coroutines.flow.Flow

interface ISessionRepository {

    suspend fun saveUserSession(userSession: UserSession)
    fun getUserSessionInfo(): Flow<UserSession?>
    fun getTheme(): Flow<Theme>
    suspend fun changeTheme(lightTheme: Boolean)
    fun existUserSession(): Boolean

    fun existUserSessionFlow(): Flow<Boolean>

}
