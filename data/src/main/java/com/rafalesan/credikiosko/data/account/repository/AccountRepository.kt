package com.rafalesan.credikiosko.data.account.repository

import com.rafalesan.credikiosko.data.account.mappers.toUserSessionData
import com.rafalesan.credikiosko.data.auth.datasource.local.UserSessionDataSource
import com.rafalesan.credikiosko.domain.account.repository.IAccountRepository
import com.rafalesan.credikiosko.domain.auth.entity.UserSession

class AccountRepository(private val userSessionDataSource: UserSessionDataSource) : IAccountRepository {

    override suspend fun saveUserSession(userSession: UserSession) {
        userSessionDataSource.saveUserSession(userSession = userSession.toUserSessionData())
    }

}
