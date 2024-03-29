package com.rafalesan.credikiosko.core.auth.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SignupRequestDto(@Json(name = "name")
                            val name: String,
                            @Json(name = "nickname")
                            val nickname: String,
                            @Json(name = "business_name")
                            val businessName: String,
                            @Json(name = "email")
                            val email: String,
                            @Json(name = "password")
                            val password: String,
                            @Json(name = "password_confirmation")
                            val passwordConfirmation: String,
                            @Json(name = "device_name")
                            val deviceName: String)
