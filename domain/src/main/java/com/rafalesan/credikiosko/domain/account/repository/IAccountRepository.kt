package com.rafalesan.credikiosko.domain.account.repository

import com.rafalesan.credikiosko.domain.auth.entity.UserSession

interface IAccountRepository {

    suspend fun saveUserSession(userSession: UserSession)

}
