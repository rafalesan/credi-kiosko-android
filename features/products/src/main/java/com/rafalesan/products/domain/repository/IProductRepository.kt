package com.rafalesan.products.domain.repository

import com.rafalesan.credikiosko.core.commons.domain.utils.ResultOf
import com.rafalesan.products.domain.entity.Product

interface IProductRepository {
    suspend fun requestProducts(): ResultOf<List<Product>, Nothing>
}
