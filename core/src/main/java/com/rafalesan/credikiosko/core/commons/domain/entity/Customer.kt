package com.rafalesan.credikiosko.core.commons.domain.entity

data class Customer(
    val id: Long,
    val name: String,
    val nickname: String? = null,
    val email: String? = null
)
