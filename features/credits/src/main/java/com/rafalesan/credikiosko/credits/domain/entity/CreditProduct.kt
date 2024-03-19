package com.rafalesan.credikiosko.credits.domain.entity

data class CreditProduct(
    val id: Long = 0,
    val creditId: Long,
    val productId: Long,
    val productName: String,
    val productPrice: String,
    val quantity: String,
    val total: String
)
