package com.rafalesan.credikiosko.products.domain.usecase

import com.rafalesan.credikiosko.products.domain.entity.Product
import com.rafalesan.credikiosko.products.domain.repository.IProductRepository
import javax.inject.Inject

class DeleteProductUseCase @Inject constructor(
    private val productRepository: IProductRepository
) {

    suspend operator fun invoke(product: Product) {
        productRepository.deleteProduct(product)
    }

}
