package com.rafalesan.credikiosko.customers.domain.usecase

import com.rafalesan.credikiosko.core.commons.domain.entity.Customer
import com.rafalesan.credikiosko.customers.domain.repository.ICustomerRepository
import javax.inject.Inject

class DeleteCustomerUseCase @Inject constructor(
    private val customerRepository: ICustomerRepository
) {

    suspend operator fun invoke(customer: Customer) {
        customerRepository.deleteCustomer(customer)
    }

}
