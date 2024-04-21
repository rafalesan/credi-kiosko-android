package com.rafalesan.credikiosko.home.domain.usecase

import com.rafalesan.credikiosko.core.commons.domain.repository.IBusinessRepository
import javax.inject.Inject

class UpdateBusinessNameUseCase @Inject constructor(
    private val businessRepository: IBusinessRepository
) {

    suspend operator fun invoke(businessName: String?) {
        businessRepository.updateBusinessName(businessName!!)
    }

}
