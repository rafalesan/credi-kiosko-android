package com.rafalesan.credikiosko.onboarding.domain.usecase

import com.rafalesan.credikiosko.core.commons.domain.utils.ResultOf
import com.rafalesan.credikiosko.onboarding.domain.repository.IBusinessRepository
import com.rafalesan.credikiosko.onboarding.domain.validator.BusinessInputValidator
import javax.inject.Inject

class SaveBusinessUseCase @Inject constructor(
    private val businessRepository: IBusinessRepository
) {

    suspend operator fun invoke(
        businessName: String
    ): ResultOf<Unit, BusinessInputValidator.BusinessInputValidation> {

        val validations = listOfNotNull(
            BusinessInputValidator.validateBusinessName(businessName)
        )

        if (validations.isNotEmpty()) {
            return ResultOf.InvalidData(validations)
        }

        businessRepository.saveBusiness(businessName)
        return ResultOf.Success(Unit)
    }

}
