package com.rafalesan.credikiosko.credits.presentation.credit_viewer

import com.rafalesan.credikiosko.core.commons.domain.entity.CreditProduct

data class CreditViewerState(
    val customerName: String? = null,
    val creditDateTime: String? = null,
    val creditTotal: String? = null,
    val creditProducts: List<CreditProduct> = emptyList()
)
