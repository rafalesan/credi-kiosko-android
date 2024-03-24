package com.rafalesan.credikiosko.core.commons.domain.entity

data class CreditProduct(
    val id: Long = 0,
    val creditId: Long = 0,
    val productId: Long = 0,
    val productName: String,
    val productPrice: String,
    val quantity: String,
    val total: String
)
