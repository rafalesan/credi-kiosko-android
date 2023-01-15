package com.rafalesan.credikiosko.core.commons.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserSessionData(@Json(name = "userId")
                           var userId: Long,
                           @Json(name = "businessId")
                           var businessId: Long,
                           @Json(name = "name")
                           var name: String,
                           @Json(name = "nickname")
                           var nickname: String,
                           @Json(name = "email")
                           var email: String,
                           @Json(name = "token")
                           var token: String)
