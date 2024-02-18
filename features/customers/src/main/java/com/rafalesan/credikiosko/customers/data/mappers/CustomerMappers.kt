package com.rafalesan.credikiosko.customers.data.mappers

import com.rafalesan.credikiosko.core.room.entity.CustomerEntity
import com.rafalesan.credikiosko.customers.domain.entity.Customer

fun CustomerEntity.toCustomerDomain(): Customer {
    return Customer(
        id,
        name,
        nickname,
        email
    )
}

fun Customer.toCustomerEntity(): CustomerEntity {
    return CustomerEntity(
        id,
        name,
        nickname,
        email
    )
}