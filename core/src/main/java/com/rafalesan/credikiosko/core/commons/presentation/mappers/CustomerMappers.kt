package com.rafalesan.credikiosko.core.commons.presentation.mappers

import com.rafalesan.credikiosko.core.commons.domain.entity.Customer
import com.rafalesan.credikiosko.core.commons.presentation.models.CustomerParcelable

fun Customer.toCustomerParcelable() : CustomerParcelable {
    return CustomerParcelable(
        id,
        name,
        nickname,
        email
    )
}

fun CustomerParcelable.toCustomerDomain(): Customer {
    return Customer(
        id,
        name,
        nickname,
        email
    )
}