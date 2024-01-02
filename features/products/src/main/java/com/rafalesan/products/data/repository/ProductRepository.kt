package com.rafalesan.products.data.repository

import com.rafalesan.credikiosko.core.commons.data.utils.ApiResultHandler
import com.rafalesan.credikiosko.core.commons.domain.utils.ResultOf
import com.rafalesan.products.data.datasource.ProductRemoteDataSource
import com.rafalesan.products.data.mappers.toProductDomain
import com.rafalesan.products.domain.entity.Product
import com.rafalesan.products.domain.repository.IProductRepository

class ProductRepository(
    private val productRemoteDataSource: ProductRemoteDataSource
) : IProductRepository {

    override suspend fun requestProducts(): ResultOf<List<Product>, Nothing> {

        val apiResult = productRemoteDataSource.requestProducts()

        return ApiResultHandler.handle(apiResult) { response ->
            val productsDomain = response.data
                .map { it.toProductDomain() }
            ResultOf.Success(productsDomain)
        }

    }
}