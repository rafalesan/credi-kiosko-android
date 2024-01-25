package com.rafalesan.products.domain.usecase

import com.rafalesan.credikiosko.core.commons.domain.utils.ResultOf
import com.rafalesan.products.domain.entity.Product
import com.rafalesan.products.domain.repository.IProductRepository
import javax.inject.Inject

@Deprecated("Use GetPagingProductUseCase instead")
class GetProductsUseCase @Inject constructor(
    private val productRepository: IProductRepository
) {

    suspend operator fun invoke(): ResultOf<List<Product>, Nothing> {
        return productRepository.requestProducts()
    }

}
