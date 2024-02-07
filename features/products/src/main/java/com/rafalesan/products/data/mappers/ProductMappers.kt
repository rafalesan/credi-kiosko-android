package com.rafalesan.products.data.mappers

import com.rafalesan.credikiosko.core.room.entity.ProductEntity
import com.rafalesan.products.data.dto.ProductDto
import com.rafalesan.products.domain.entity.Product

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