package com.rafalesan.products.domain.usecase

import androidx.paging.PagingData
import com.rafalesan.products.domain.entity.Product
import com.rafalesan.products.domain.repository.IProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPagingProductUseCase @Inject constructor(
    private val productRepository: IProductRepository
) {

    suspend operator fun invoke(): Flow<PagingData<Product>> {
        return productRepository.requestProductsPaged()
    }

}
