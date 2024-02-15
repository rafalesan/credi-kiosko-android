package com.rafalesan.products.domain.usecase

import com.rafalesan.credikiosko.core.commons.domain.utils.ResultOf
import com.rafalesan.products.domain.entity.Product
import com.rafalesan.products.domain.repository.IProductRepository
import com.rafalesan.products.domain.validator.ProductValidator
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
