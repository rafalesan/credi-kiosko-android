package com.rafalesan.credikiosko.data.base

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ErrorResponse(@Json(name = "message")
                         var message: String?,
                         @Json(name = "errors")
                         var errors: Map<String, List<String>>?)
