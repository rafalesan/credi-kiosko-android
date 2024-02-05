package com.rafalesan.credikiosko.onboarding.domain.validator

import com.rafalesan.credikiosko.onboarding.R

object BusinessInputValidator {

    fun validateBusinessName(businessName: String?): BusinessInputValidation? {
        if (businessName.isNullOrBlank()) {
            return BusinessInputValidation.EMPTY_BUSINESS_NAME
        }
        return null
    }

    enum class BusinessInputValidation(val errorResId: Int) {
        EMPTY_BUSINESS_NAME(R.string.empty_business_name_validation_desc)
    }

}
