package com.rafalesan.credikiosko.core.auth.domain.auth.usecases

import com.rafalesan.credikiosko.core.commons.domain.repository.ISessionRepository
import javax.inject.Inject

class ExistUserSessionFlowUseCase @Inject constructor(
    private val sessionRepository: ISessionRepository
) {

    operator fun invoke() = sessionRepository.existUserSessionFlow()

}
