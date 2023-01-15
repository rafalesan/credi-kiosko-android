package com.rafalesan.credikiosko.core.commons.data.base

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BaseResponse<T>(@Json(name = "message")
                           var message: String?,
                           @Json(name = "data")
                           var data: T)
