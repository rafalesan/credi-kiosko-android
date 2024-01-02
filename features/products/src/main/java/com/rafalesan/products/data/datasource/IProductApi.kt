package com.rafalesan.products.data.datasource

import com.rafalesan.credikiosko.core.commons.data.base.PaginatedResponse
import com.rafalesan.products.data.dto.ProductDto
import retrofit2.Response
import retrofit2.http.GET

interface IProductApi {

    //TODO: ADD PAGE ARGUMENT
    @GET("products")
    suspend fun requestProducts(): Response<PaginatedResponse<ProductDto>>

}
