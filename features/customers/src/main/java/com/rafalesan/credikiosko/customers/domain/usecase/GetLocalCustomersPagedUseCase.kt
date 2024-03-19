package com.rafalesan.credikiosko.customers.domain.usecase

import androidx.paging.PagingData
import com.rafalesan.credikiosko.core.commons.domain.entity.Customer
import com.rafalesan.credikiosko.customers.domain.repository.ICustomerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocalCustomersPagedUseCase @Inject constructor(
    private val customerRepository: ICustomerRepository
) {

    operator fun invoke(): Flow<PagingData<Customer>> {
        return customerRepository.getLocalCustomerPaged()
    }

}
