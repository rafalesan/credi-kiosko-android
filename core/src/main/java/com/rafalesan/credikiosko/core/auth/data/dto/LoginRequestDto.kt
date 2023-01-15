package com.rafalesan.credikiosko.core.auth.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginRequestDto(@Json(name = "email")
                           val email: String,
                           @Json(name = "password")
                           val password: String,
                           @Json(name = "device_name")
                           val deviceName: String)
