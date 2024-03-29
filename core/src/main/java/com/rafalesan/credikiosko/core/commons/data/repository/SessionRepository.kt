package com.rafalesan.credikiosko.core.commons.data.repository

import com.rafalesan.credikiosko.core.commons.data.datasource.local.UserPreferenceDataSource
import com.rafalesan.credikiosko.core.commons.data.datasource.local.UserSessionDataSource
import com.rafalesan.credikiosko.core.commons.data.mappers.toUserSessionData
import com.rafalesan.credikiosko.core.commons.data.mappers.toUserSessionDomain
import com.rafalesan.credikiosko.core.commons.domain.entity.UserSession
import com.rafalesan.credikiosko.core.commons.domain.repository.ISessionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class SessionRepository(private val userSessionDataSource: UserSessionDataSource,
                        private val userPreferenceDataSource: UserPreferenceDataSource) : ISessionRepository {

    override suspend fun saveUserSession(userSession: UserSession) {
        userSessionDataSource.saveUserSession(userSession = userSession.toUserSessionData())
    }

    override fun getUserSessionInfo(): Flow<UserSession?> {
        return userSessionDataSource.userSession().map { it?.toUserSessionDomain() }
    }

    override fun getTheme() = userPreferenceDataSource.getTheme()

    override suspend fun changeTheme(lightTheme: Boolean) {
        userPreferenceDataSource.saveTheme(lightTheme)
    }

    override fun existUserSession(): Boolean {
        val userSession = runBlocking { userSessionDataSource.userSession().first() }
        return userSession != null
    }

    override fun existUserSessionFlow(): Flow<Boolean> {
        return userSessionDataSource
            .userSession()
            .map { it != null }
    }
}
