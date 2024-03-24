package com.rafalesan.credikiosko.core.commons.presentation.models

import android.os.Parcelable
import com.rafalesan.credikiosko.core.commons.emptyString
import kotlinx.parcelize.Parcelize

@Parcelize
data class CreditProductParcelable(
    val id: Long = 0,
    val creditId: Long = 0,
    val productId: Long = 0,
    val productName: String = emptyString,
    val productPrice: String = emptyString,
    val quantity: String = emptyString,
    val total: String = emptyString
) : Parcelable
