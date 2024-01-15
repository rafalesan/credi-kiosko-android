package com.rafalesan.products.data.datasource

import com.rafalesan.credikiosko.core.commons.data.base.PaginatedResponse
import com.rafalesan.credikiosko.core.commons.data.utils.ApiResult
import com.rafalesan.credikiosko.core.commons.data.utils.IApiHandler
import com.rafalesan.products.data.dto.ProductDto
import javax.inject.Inject

class ProductRemoteDataSource @Inject constructor(
    private val productApi: IProductApi,
    private val apiHandler: IApiHandler
) {

    suspend fun requestProducts(page: Int = 1): ApiResult<PaginatedResponse<ProductDto>> {
        return apiHandler.performApiCall {
            productApi.requestProducts(page)
        }
    }

}
