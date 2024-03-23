package com.rafalesan.credikiosko.core.commons.presentation.models

import android.os.Parcelable
import com.rafalesan.credikiosko.core.commons.emptyString
import kotlinx.parcelize.Parcelize

@Parcelize
data class CustomerParcelable(
    val id: Long = 0,
    val name: String = emptyString,
    val nickname: String? = null,
    val email: String? = null
): Parcelable
