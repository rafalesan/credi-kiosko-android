package com.rafalesan.credikiosko.core.auth.domain.auth.validators

import com.rafalesan.credikiosko.core.commons.domain.utils.ext.isNotValidEmail

object SignupValidator {

    fun validateName(name: String?): SignupValidation? {
        return when {
            name.isNullOrBlank() -> SignupValidation.EMPTY_NAME
            name.length < 10     -> SignupValidation.MIN_10_CHARS_NAME
            else                 -> null
        }
    }

    fun validateBusinessName(businessName: String?): SignupValidation? {
        return when {
            businessName.isNullOrBlank() -> SignupValidation.EMPTY_BUSINESS_NAME
            businessName.length < 2      -> SignupValidation.MIN_2_CHARS_BUSINESS_NAME
            else -> null
        }
    }

    fun validateNickname(nickname: String?): SignupValidation? {
        return when {
            nickname.isNullOrBlank() -> SignupValidation.EMPTY_NICKNAME
            nickname.length < 3      -> SignupValidation.MIN_3_CHARS_NICKNAME
            else -> null
        }
    }

    fun validateEmail(email: String?): SignupValidation? {
        return when {
            email.isNullOrBlank()   -> SignupValidation.EMPTY_EMAIL
            email.isNotValidEmail() -> SignupValidation.INVALID_EMAIL
            else -> null
        }
    }

    fun validatePassword(password: String?, passwordConfirmation: String?): SignupValidation? {
        return when {
            password.isNullOrBlank() -> SignupValidation.EMPTY_PASSWORD
            password != passwordConfirmation -> SignupValidation.PASSWORDS_NOT_EQUALS
            else -> null
        }
    }

    fun validatePasswordConfirmation(passwordConfirmation: String?): SignupValidation? {
        return when {
            passwordConfirmation.isNullOrBlank() -> SignupValidation.EMPTY_PASSWORD_CONFIRMATION
            else -> null
        }
    }

    fun validateDeviceName(deviceName: String?): SignupValidation? {
        return when {
            deviceName.isNullOrBlank() -> SignupValidation.EMPTY_DEVICE_NAME
            else -> null
        }
    }

    enum class SignupValidation {
        EMPTY_NAME,
        MIN_10_CHARS_NAME,
        EMPTY_BUSINESS_NAME,
        MIN_2_CHARS_BUSINESS_NAME,
        EMPTY_NICKNAME,
        MIN_3_CHARS_NICKNAME,
        EMPTY_EMAIL,
        INVALID_EMAIL,
        EMPTY_PASSWORD,
        EMPTY_PASSWORD_CONFIRMATION,
        PASSWORDS_NOT_EQUALS,
        EMPTY_DEVICE_NAME
    }

}
