package com.rafalesan.credikiosko.core.commons.presentation.mappers

import com.rafalesan.credikiosko.core.commons.domain.entity.Product
import com.rafalesan.credikiosko.core.commons.presentation.models.ProductParcelable

fun Product.toProductParcelable() : ProductParcelable {
    return ProductParcelable(
        id,
        name,
        price
    )
}

fun ProductParcelable.toProductDomain(): Product {
    return Product(
        id,
        name,
        price
    )
}