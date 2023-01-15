package com.rafalesan.credikiosko.core.auth.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthResponseDto(@Json(name = "token")
                           var token: String,
                           @Json(name = "user")
                           val user: UserDto)
