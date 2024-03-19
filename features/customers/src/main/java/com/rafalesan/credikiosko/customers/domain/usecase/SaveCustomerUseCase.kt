package com.rafalesan.credikiosko.customers.domain.usecase

import com.rafalesan.credikiosko.core.commons.domain.entity.Customer
import com.rafalesan.credikiosko.core.commons.domain.utils.ResultOf
import com.rafalesan.credikiosko.customers.domain.repository.ICustomerRepository
import com.rafalesan.credikiosko.customers.domain.validator.CustomerInputsValidator
import javax.inject.Inject

class SaveCustomerUseCase @Inject constructor(
    private val customerRepository: ICustomerRepository
) {

    suspend operator fun invoke(
        customer: Customer
    ): ResultOf<Unit, CustomerInputsValidator.CustomerInputValidation> {

        val validations = CustomerInputsValidator.validateCustomerInputs(
            customer.name,
            customer.email
        )

        if (validations.isNotEmpty()) {
            return ResultOf.Failure.InvalidData(validations)
        }

        customerRepository.saveCustomer(customer)

        return ResultOf.Success(Unit)

    }

}
