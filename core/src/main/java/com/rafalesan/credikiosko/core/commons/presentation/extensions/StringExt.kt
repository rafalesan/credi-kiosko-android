package com.rafalesan.credikiosko.core.commons.presentation.extensions

import androidx.core.util.PatternsCompat

fun String?.isValidEmail(): Boolean {
    this ?: return false
    return PatternsCompat.EMAIL_ADDRESS.matcher(this).matches()
}

fun String?.isNotValidEmail(): Boolean {
    return !this.isValidEmail()
}