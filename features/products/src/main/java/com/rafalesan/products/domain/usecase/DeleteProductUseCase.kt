package com.rafalesan.products.domain.usecase

import com.rafalesan.products.domain.entity.Product
import com.rafalesan.products.domain.repository.IProductRepository
import javax.inject.Inject

class DeleteProductUseCase @Inject constructor(
    private val productRepository: IProductRepository
) {

    suspend operator fun invoke(product: Product) {
        productRepository.deleteProduct(product)
    }

}
