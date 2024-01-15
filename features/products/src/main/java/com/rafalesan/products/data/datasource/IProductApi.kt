package com.rafalesan.products.data.datasource

import com.rafalesan.credikiosko.core.commons.data.base.PaginatedResponse
import com.rafalesan.products.data.dto.ProductDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface IProductApi {
    @GET("products")
    suspend fun requestProducts(
        @Query("page")
        page: Int
    ): Response<PaginatedResponse<ProductDto>>

}
