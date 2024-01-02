package com.rafalesan.products.data.dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductDto(
    @Json(name = "business_id")
    val businessId: Long,
    @Json(name = "created_at")
    val createdAt: String?,
    @Json(name = "deleted_at")
    val deletedAt: String?,
    @Json(name = "id")
    val id: Long,
    @Json(name = "name")
    val name: String,
    @Json(name = "price")
    val price: String,
    @Json(name = "updated_at")
    val updatedAt: String?
)