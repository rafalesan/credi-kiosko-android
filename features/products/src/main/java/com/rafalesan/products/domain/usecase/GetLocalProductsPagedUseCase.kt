package com.rafalesan.products.domain.usecase

import androidx.paging.PagingData
import com.rafalesan.products.domain.entity.Product
import com.rafalesan.products.domain.repository.IProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocalProductsPagedUseCase @Inject constructor(
    private val productRepository: IProductRepository
) {

    operator fun invoke(): Flow<PagingData<Product>> {
        return productRepository.getLocalProductsPaged()
    }

}
