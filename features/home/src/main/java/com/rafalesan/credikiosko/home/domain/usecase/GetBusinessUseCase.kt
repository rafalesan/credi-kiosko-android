package com.rafalesan.credikiosko.home.domain.usecase

import com.rafalesan.credikiosko.core.commons.domain.entity.Business
import com.rafalesan.credikiosko.core.commons.domain.repository.IBusinessRepository
import javax.inject.Inject

class GetBusinessUseCase @Inject constructor(
    private val businessRepository: IBusinessRepository
) {

    suspend operator fun invoke(): Business {
        return businessRepository.getBusiness()
    }

}
