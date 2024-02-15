package com.rafalesan.credikiosko.core.commons

import com.rafalesan.credikiosko.core.commons.domain.entity.Business
import com.rafalesan.credikiosko.core.commons.domain.entity.UserSession


val emptyBusiness = Business(
    0,
    "",
    ""
)

val emptyUserSession = UserSession(
    0,
    0,
    "",
    "",
    "",
    "",
    emptyBusiness
)

const val emptyString = ""
const val zeroLong = 0L