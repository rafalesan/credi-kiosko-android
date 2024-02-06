package com.rafalesan.credikiosko.core.auth.data.dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BusinessDto(
    @Json(name = "id")
    val id: Long,
    @Json(name = "name")
    val name: String,
    @Json(name = "email")
    val email: String?,
    @Json(name = "updated_at")
    val updatedAt: String,
    @Json(name = "created_at")
    val createdAt: String,
    @Json(name = "deleted_at")
    val deletedAt: String?
)
