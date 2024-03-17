package com.rafalesan.credikiosko.products.data.mappers

import com.rafalesan.credikiosko.core.room.entity.ProductEntity
import com.rafalesan.credikiosko.products.data.dto.ProductDto
import com.rafalesan.credikiosko.products.domain.entity.Product

fun ProductDto.toProductDomain(): Product {
    return Product(
        id,
        name,
        price
    )
}

fun ProductEntity.toProductDomain(): Product {
    return Product(
        id,
        name,
        price
    )
}

fun Product.toProductEntity(): ProductEntity {
    return ProductEntity(
        id,
        name,
        price
    )
}