package com.rafalesan.credikiosko.domain.auth.validators

import com.rafalesan.credikiosko.core.commons.domain.utils.ext.isNotValidEmail

object CredentialsValidator {

    fun validateEmail(email: String?): CredentialValidation? {
        return when {
            email.isNullOrBlank()   -> CredentialValidation.EMPTY_EMAIL
            email.isNotValidEmail() -> CredentialValidation.INVALID_EMAIL
            else -> null
        }
    }

    fun validatePassword(password: String?): CredentialValidation? {
        return when {
            password.isNullOrBlank() -> CredentialValidation.EMPTY_PASSWORD
            else -> null
        }
    }

    fun validateDeviceName(deviceName: String?): CredentialValidation? {
        return when {
            deviceName.isNullOrBlank() -> CredentialValidation.EMPTY_DEVICE_NAME
            else -> null
        }
    }

    enum class CredentialValidation {
        EMPTY_EMAIL,
        INVALID_EMAIL,
        EMPTY_PASSWORD,
        EMPTY_DEVICE_NAME,
    }

}
