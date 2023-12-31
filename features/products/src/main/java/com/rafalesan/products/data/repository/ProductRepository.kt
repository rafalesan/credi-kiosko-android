package com.rafalesan.products.data.repository

import com.rafalesan.credikiosko.core.commons.domain.utils.ResultOf
import com.rafalesan.products.domain.entity.Product
import com.rafalesan.products.domain.repository.IProductRepository

class ProductRepository : IProductRepository {
    override suspend fun requestProducts(): ResultOf<List<Product>, Nothing> {
        return ResultOf.Success(
            listOf(
                Product(
                    1,
                    "Papitas fritas",
                    "20.0000"
                ),
                Product(
                    2,
                    "Caramelo",
                    "2.0000"
                ),
                Product(
                    3,
                    "Almuerzo completo",
                    "100.0000"
                ),
                Product(
                    4,
                    "Lapicero",
                    "8.0000"
                ),
                Product(
                    5,
                    "Taza de café",
                    "7.0000"
                ),
                Product(
                    6,
                    "Papita Ziba",
                    "10.0000"
                ),
            )
        )
    }
}