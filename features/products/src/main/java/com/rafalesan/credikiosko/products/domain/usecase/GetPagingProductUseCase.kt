package com.rafalesan.credikiosko.products.domain.usecase

import androidx.paging.PagingData
import com.rafalesan.credikiosko.products.domain.entity.Product
import com.rafalesan.credikiosko.products.domain.repository.IProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPagingProductUseCase @Inject constructor(
    private val productRepository: IProductRepository
) {

    suspend operator fun invoke(): Flow<PagingData<Product>> {
        return productRepository.requestProductsPaged()
    }

}
