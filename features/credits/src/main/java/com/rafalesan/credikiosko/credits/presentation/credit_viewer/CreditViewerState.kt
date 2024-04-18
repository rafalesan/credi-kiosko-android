package com.rafalesan.credikiosko.credits.presentation.credit_viewer

import com.rafalesan.credikiosko.core.commons.domain.entity.CreditProduct
import com.rafalesan.credikiosko.core.commons.zeroLong

data class CreditViewerState(
    val creditId: Long = zeroLong,
    val customerName: String? = null,
    val creditDateTime: String? = null,
    val creditTotal: String? = null,
    val creditProducts: List<CreditProduct> = emptyList(),
    val printLoadingStringResId: Int? = null,
    val printerConnectionError: Int? = null,
    val printerThatConnectionFailed: String? = null,
    val isShowingPrinterNotConfiguredMessage: Boolean = false,
    val isShowingPrinterConfiguration: Boolean = false
)
