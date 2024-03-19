package com.rafalesan.credikiosko.credits.data.mappers

import com.rafalesan.credikiosko.core.commons.data.mappers.toCustomerDomain
import com.rafalesan.credikiosko.core.room.entity.CreditEntity
import com.rafalesan.credikiosko.core.room.entity.CreditProductEntity
import com.rafalesan.credikiosko.core.room.entity.CreditWithCustomerAndProductsEntity
import com.rafalesan.credikiosko.credits.domain.entity.Credit
import com.rafalesan.credikiosko.credits.domain.entity.CreditProduct
import com.rafalesan.credikiosko.credits.domain.entity.CreditWithCustomerAndProducts

fun CreditEntity.toCreditDomain(): Credit {
    return Credit(
        id,
        businessId,
        customerId,
        date,
        total
    )
}

fun CreditProductEntity.toCreditProductDomain(): CreditProduct {
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

fun CreditWithCustomerAndProductsEntity.toCreditWithCustomerAndProductsDomain(): CreditWithCustomerAndProducts {
    return CreditWithCustomerAndProducts(
        credit.toCreditDomain(),
        customer.toCustomerDomain(),
        products.map { it.toCreditProductDomain() }
    )
}

fun Credit.toCreditEntity(): CreditEntity {
    return CreditEntity(
        id,
        businessId,
        customerId,
        date,
        total
    )
}

fun CreditProduct.toCreditProductEntity(): CreditProductEntity {
    return CreditProductEntity(
        id,
        creditId,
        productId,
        productName,
        productPrice,
        quantity,
        total
    )
}
