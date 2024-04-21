package com.rafalesan.credikiosko.home.domain.usecase

import com.rafalesan.credikiosko.core.commons.domain.exception.ValidationsException
import com.rafalesan.credikiosko.core.commons.domain.repository.IBusinessRepository
import com.rafalesan.credikiosko.core.commons.domain.utils.Result
import com.rafalesan.credikiosko.core.commons.domain.validator.BusinessInputValidator
import javax.inject.Inject

class UpdateBusinessNameUseCase @Inject constructor(
    private val businessRepository: IBusinessRepository
) {

    suspend operator fun invoke(businessName: String?) : Result<Unit> {

        val validation = BusinessInputValidator.validateBusinessName(businessName)

        if (validation != null) {
            return Result.Error(
                ValidationsException(listOf(validation))
            )
        }

        businessRepository.updateBusinessName(businessName!!)
        return Result.Success(Unit)
    }

}
