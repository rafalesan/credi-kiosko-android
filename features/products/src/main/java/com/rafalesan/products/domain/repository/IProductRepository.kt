package com.rafalesan.products.domain.repository

import androidx.paging.PagingData
import com.rafalesan.products.domain.entity.Product
import kotlinx.coroutines.flow.Flow

interface IProductRepository {
    suspend fun requestProductsPaged(): Flow<PagingData<Product>>

    fun getLocalProductsPaged(): Flow<PagingData<Product>>

    suspend fun saveProduct(product: Product)

    suspend fun deleteProduct(product: Product)

}
