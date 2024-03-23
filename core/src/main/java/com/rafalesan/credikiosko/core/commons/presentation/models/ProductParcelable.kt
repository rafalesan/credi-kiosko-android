package com.rafalesan.credikiosko.core.commons.presentation.models

import android.os.Parcelable
import com.rafalesan.credikiosko.core.commons.emptyString
import com.rafalesan.credikiosko.core.commons.zeroLong
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductParcelable(
    val id: Long = zeroLong,
    val name: String = emptyString,
    val price: String = emptyString
) : Parcelable
