package com.rafalesan.products.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.rafalesan.credikiosko.core.commons.data.utils.ApiResultHandler
import com.rafalesan.credikiosko.core.commons.domain.utils.ResultOf
import com.rafalesan.products.data.datasource.ProductPagingSource
import com.rafalesan.products.data.datasource.ProductRemoteDataSource
import com.rafalesan.products.data.mappers.toProductDomain
import com.rafalesan.products.domain.entity.Product
import com.rafalesan.products.domain.repository.IProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProductRepository(
    private val productRemoteDataSource: ProductRemoteDataSource,
) : IProductRepository {

    override suspend fun requestProducts(): ResultOf<List<Product>, Nothing> {

        val apiResult = productRemoteDataSource.requestProducts()

        return ApiResultHandler.handle(apiResult) { response ->
            val productsDomain = response.data
                .map { it.toProductDomain() }
            ResultOf.Success(productsDomain)
        }

    }

    override suspend fun requestProductsPaged(): Flow<PagingData<Product>> {
        return Pager(
            config = PagingConfig(pageSize = 15, prefetchDistance = 2),
            pagingSourceFactory = {
                ProductPagingSource(productRemoteDataSource)
            }
        )
            .flow
            .map { pagingData ->
                pagingData.map {
                    it.toProductDomain()
                }
            }
    }

}
