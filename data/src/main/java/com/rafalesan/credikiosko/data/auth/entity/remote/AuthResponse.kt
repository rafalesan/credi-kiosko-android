package com.rafalesan.credikiosko.data.auth.entity.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthResponse(@Json(name = "token")
                         var token: String,
                        @Json(name = "user")
                         val user: UserResponse)
