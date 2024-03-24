package com.rafalesan.credikiosko.core.commons.presentation.mappers

import com.rafalesan.credikiosko.core.commons.domain.entity.CreditProduct
import com.rafalesan.credikiosko.core.commons.presentation.models.CreditProductParcelable

fun CreditProduct.toCreditProductParcelable(): CreditProductParcelable {
    return CreditProductParcelable(
        id,
        creditId,
        productId,
        productName,
        productPrice,
        quantity,
        total
    )
}

fun CreditProductParcelable.toCreditProductDomain(): CreditProduct {
    return CreditProduct(
        id,
        creditId,
        productId,
        productName,
        productPrice,
        quantity,
        total
    )
}