package com.rafalesan.products.domain.repository

import androidx.paging.PagingData
import com.rafalesan.credikiosko.core.commons.domain.utils.ResultOf
import com.rafalesan.products.domain.entity.Product
import kotlinx.coroutines.flow.Flow

interface IProductRepository {
    @Deprecated("Use GetPagingProductUseCase instead")
    suspend fun requestProducts(): ResultOf<List<Product>, Nothing>
    suspend fun requestProductsPaged(): Flow<PagingData<Product>>

    fun getLocalProductsPaged(): Flow<PagingData<Product>>

    suspend fun saveProduct(product: Product)

}
