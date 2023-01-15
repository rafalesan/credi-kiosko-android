package com.rafalesan.credikiosko.core.commons.domain.utils.ext

import java.util.regex.Pattern

fun String?.isValidEmail(): Boolean {
    this ?: return false
    val emailPattern = Pattern.compile(
            "[a-zA-Z0-9+._%\\-]{1,256}" +
                    "@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+")

    return emailPattern.matcher(this).matches()
}

fun String?.isNotValidEmail(): Boolean {
    return !this.isValidEmail()
}