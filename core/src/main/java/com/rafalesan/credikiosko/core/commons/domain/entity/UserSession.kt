package com.rafalesan.credikiosko.core.commons.domain.entity

data class UserSession(
    var userId: Long,
    var businessId: Long,
    var name: String,
    var nickname: String,
    var email: String,
    var token: String,
    var business: Business
)
