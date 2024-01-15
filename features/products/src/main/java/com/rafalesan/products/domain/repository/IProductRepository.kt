package com.rafalesan.products.domain.repository

import androidx.paging.PagingData
import com.rafalesan.credikiosko.core.commons.domain.utils.ResultOf
import com.rafalesan.products.domain.entity.Product
import kotlinx.coroutines.flow.Flow

interface IProductRepository {
    suspend fun requestProducts(): ResultOf<List<Product>, Nothing>
    suspend fun requestProductsPaged(): Flow<PagingData<Product>>
}
