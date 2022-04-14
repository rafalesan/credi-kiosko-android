package com.rafalesan.credikiosko.presentation.extensions

import androidx.core.util.PatternsCompat

fun String?.isValidEmail(): Boolean {
    this ?: return false
    return PatternsCompat.EMAIL_ADDRESS.matcher(this).matches()
}

fun String?.isNotValidEmail(): Boolean {
    return !this.isValidEmail()
}