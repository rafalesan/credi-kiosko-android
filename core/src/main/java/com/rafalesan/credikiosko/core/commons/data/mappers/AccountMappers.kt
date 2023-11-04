package com.rafalesan.credikiosko.core.commons.data.mappers

import com.rafalesan.credikiosko.core.commons.data.dto.BusinessData
import com.rafalesan.credikiosko.core.commons.data.dto.UserSessionData
import com.rafalesan.credikiosko.core.commons.domain.entity.Business
import com.rafalesan.credikiosko.core.commons.domain.entity.UserSession

fun UserSession.toUserSessionData(): UserSessionData {
    return UserSessionData(
        userId,
        businessId,
        name,
        nickname,
        email,
        token,
        business.toBusinessData()
    )
}

fun Business.toBusinessData() = BusinessData(
    id,
    name,
    email
)

fun BusinessData.toBusiness() = Business(
    id,
    name,
    email
)

fun UserSessionData.toUserSessionDomain(): UserSession {
    return UserSession(
        userId,
        businessId,
        name,
        nickname,
        email,
        token,
        business.toBusiness()
    )
}
