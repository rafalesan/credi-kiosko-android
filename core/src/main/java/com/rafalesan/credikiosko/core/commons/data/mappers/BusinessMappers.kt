package com.rafalesan.credikiosko.core.commons.data.mappers

import com.rafalesan.credikiosko.core.commons.domain.entity.Business
import com.rafalesan.credikiosko.core.room.entity.BusinessEntity

fun BusinessEntity.toDomain() : Business {
    return Business(
        id,
        name,
        null
    )
}
