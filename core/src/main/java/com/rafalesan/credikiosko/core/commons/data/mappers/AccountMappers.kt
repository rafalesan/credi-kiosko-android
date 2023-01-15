package com.rafalesan.credikiosko.core.commons.data.mappers

import com.rafalesan.credikiosko.core.commons.data.dto.UserSessionData
import com.rafalesan.credikiosko.core.commons.domain.entity.UserSession

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
