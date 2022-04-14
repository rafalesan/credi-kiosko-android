package com.rafalesan.credikiosko.presentation.auth.login

import com.rafalesan.credikiosko.presentation.R
import com.rafalesan.credikiosko.presentation.extensions.isNotValidEmail

object CredentialsValidator {

    fun validateEmail(email: String?): Int? {
        return when {
            email.isNullOrBlank()   -> R.string.val_empty_email
            email.isNotValidEmail() -> R.string.val_invalid_email
            else -> null
        }
    }

    fun validatePassword(password: String?): Int? {
        return when {
            password.isNullOrBlank() -> R.string.val_empty_password
            else -> null
        }
    }

}
