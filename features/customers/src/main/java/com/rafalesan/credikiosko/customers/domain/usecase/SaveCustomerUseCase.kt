package com.rafalesan.credikiosko.customers.domain.usecase

import com.rafalesan.credikiosko.customers.domain.entity.Customer
import com.rafalesan.credikiosko.customers.domain.repository.ICustomerRepository
import javax.inject.Inject

class SaveCustomerUseCase @Inject constructor(
    private val customerRepository: ICustomerRepository
) {

    suspend operator fun invoke(customer: Customer) {
        //TODO: PERFORM VALIDATIONS
        customerRepository.saveCustomer(customer)
    }

}
