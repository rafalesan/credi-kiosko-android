package com.rafalesan.credikiosko.data.account.mappers

import com.rafalesan.credikiosko.data.auth.entity.local.UserSessionData
import com.rafalesan.credikiosko.domain.auth.entity.UserSession

fun UserSession.toUserSessionData(): UserSessionData {
    return UserSessionData(userId,
                           businessId,
                           name,
                           nickname,
                           email,
                           token)
}

fun UserSessionData.toUserSessionDomain(): UserSession {
    return UserSession(userId,
                       businessId,
                       name,
                       nickname,
                       email,
                       token)
}
