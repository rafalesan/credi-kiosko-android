package com.rafalesan.credikiosko.credits.domain.entity

data class Credit(
    val id: Long = 0,
    val businessId: Long,
    val customerId: Long,
    val date: String,
    val total: String
)
