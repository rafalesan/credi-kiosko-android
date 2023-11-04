package com.rafalesan.credikiosko.core.auth.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserDto(
    @Json(name = "id")
    var id: Long?,
    @Json(name = "business_id")
    var businessId: Long?,
    @Json(name = "name")
    var name: String?,
    @Json(name = "nickname")
    var nickname: String?,
    @Json(name = "email")
    var email: String?,
    @Json(name = "email_verified_at")
    var emailVerifiedAt: String?,
    @Json(name = "created_at")
    var createdAt: String?,
    @Json(name = "updated_at")
    var updatedAt: String?,
    @Json(name = "deleted_at")
    var deletedAt: String?,
    @Json(name = "business")
    val business: BusinessDto
)
