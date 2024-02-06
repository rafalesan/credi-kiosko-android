package com.rafalesan.credikiosko.core.commons.domain.usecases

import com.rafalesan.credikiosko.core.commons.domain.repository.IBusinessRepository
import javax.inject.Inject

class ExistBusinessUseCase @Inject constructor(
    private val businessRepository: IBusinessRepository
) {

    suspend operator fun invoke(): Boolean {
        return businessRepository.existsBusiness()
    }

}
