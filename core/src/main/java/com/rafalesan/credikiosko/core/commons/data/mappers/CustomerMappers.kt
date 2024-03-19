package com.rafalesan.credikiosko.core.commons.data.mappers

import com.rafalesan.credikiosko.core.commons.domain.entity.Customer
import com.rafalesan.credikiosko.core.room.entity.CustomerEntity

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