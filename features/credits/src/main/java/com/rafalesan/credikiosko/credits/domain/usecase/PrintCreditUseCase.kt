package com.rafalesan.credikiosko.credits.domain.usecase

import com.rafalesan.credikiosko.core.bluetooth_printer.PrintStatus
import com.rafalesan.credikiosko.credits.domain.repository.ICreditRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PrintCreditUseCase @Inject constructor(
    private val creditRepository: ICreditRepository
) {

    operator fun invoke(creditId: Long): Flow<PrintStatus> {
        return creditRepository.printCredit(creditId)
    }

}
