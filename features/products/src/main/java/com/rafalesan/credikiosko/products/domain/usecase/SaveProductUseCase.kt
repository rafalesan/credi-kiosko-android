package com.rafalesan.credikiosko.products.domain.usecase

import com.rafalesan.credikiosko.core.commons.domain.utils.ResultOf
import com.rafalesan.credikiosko.products.domain.entity.Product
import com.rafalesan.credikiosko.products.domain.repository.IProductRepository
import com.rafalesan.credikiosko.products.domain.validator.ProductValidator
import javax.inject.Inject

class SaveProductUseCase @Inject constructor(
    private val productRepository: IProductRepository
) {

    suspend operator fun invoke(
        product: Product
    ): ResultOf<Unit, ProductValidator.ProductInputValidation> {

        val validations = ProductValidator.validateProductInputs(
            product.name,
            product.price
        )

        if (validations.isNotEmpty()) {
            return ResultOf.Failure.InvalidData(validations)
        }

        productRepository.saveProduct(product)
        return ResultOf.Success(Unit)
    }

}
