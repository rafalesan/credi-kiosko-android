package com.rafalesan.credikiosko.products.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.rafalesan.credikiosko.core.commons.domain.entity.Product
import com.rafalesan.credikiosko.products.data.datasource.ProductLocalDataSource
import com.rafalesan.credikiosko.products.data.datasource.ProductPagingSource
import com.rafalesan.credikiosko.products.data.datasource.ProductRemoteDataSource
import com.rafalesan.credikiosko.products.data.mappers.toProductDomain
import com.rafalesan.credikiosko.products.data.mappers.toProductEntity
import com.rafalesan.credikiosko.products.domain.repository.IProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProductRepository(
    private val productRemoteDataSource: ProductRemoteDataSource,
    private val productLocalDataSource: ProductLocalDataSource
) : IProductRepository {

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

    override fun getLocalProductsPaged(): Flow<PagingData<Product>> {
        return Pager(
            config = PagingConfig(pageSize = 15, prefetchDistance = 2),
            pagingSourceFactory = {
                productLocalDataSource.getProductsPaged()
            }
        ).flow
            .map { pagingData ->
                pagingData.map { it.toProductDomain() }
            }
    }

    override suspend fun saveProduct(product: Product) {
        productLocalDataSource.saveProduct(product.toProductEntity())
    }

    override suspend fun deleteProduct(product: Product) {
        productLocalDataSource.deleteProduct(product.toProductEntity())
    }
}
