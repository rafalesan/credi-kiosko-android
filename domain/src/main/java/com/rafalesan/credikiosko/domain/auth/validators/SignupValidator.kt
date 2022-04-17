package com.rafalesan.credikiosko.domain.auth.validators

object SignupValidator {

    enum class SignupValidation {
        EMPTY_NAME,
        EMPTY_BUSINESS_NAME,
        EMPTY_NICKNAME,
        EMPTY_EMAIL,
        EMPTY_PASSWORD,
        EMPTY_PASSWORD_CONFIRMATION,
        EMPTY_DEVICE_NAME
    }

}
