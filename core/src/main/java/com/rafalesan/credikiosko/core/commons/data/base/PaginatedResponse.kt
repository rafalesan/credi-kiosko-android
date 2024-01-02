package com.rafalesan.credikiosko.core.commons.data.base


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PaginatedResponse<T>(
    @Json(name = "data")
    val data: List<T>,
    @Json(name = "current_page")
    val currentPage: Int,
    @Json(name = "first_page_url")
    val firstPageUrl: String,
    @Json(name = "from")
    val from: Int,
    @Json(name = "next_page_url")
    val nextPageUrl: String?,
    @Json(name = "path")
    val path: String,
    @Json(name = "per_page")
    val perPage: Int?,
    @Json(name = "prev_page_url")
    val prevPageUrl: String?,
    @Json(name = "to")
    val to: Int
)