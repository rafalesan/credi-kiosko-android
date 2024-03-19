package com.rafalesan.credikiosko.core.room.entity

import androidx.room.Embedded
import androidx.room.Relation

data class CreditWithCustomerAndProductsEntity(
    @Embedded val credit: CreditEntity,
    @Relation(
        parentColumn = "customerId",
        entityColumn = "id"
    )
    val customer: CustomerEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "creditId"
    )
    val products: List<CreditProductEntity>
)
