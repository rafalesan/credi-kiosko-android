package com.rafalesan.credikiosko.products.data.datasource

import androidx.paging.PagingSource
import com.rafalesan.credikiosko.core.room.dao.ProductDao
import com.rafalesan.credikiosko.core.room.entity.ProductEntity
import javax.inject.Inject

class ProductLocalDataSource @Inject constructor(
    private val productDao: ProductDao
) {

    suspend fun saveProduct(product: ProductEntity) {
        productDao.insertProduct(product)
    }

    suspend fun deleteProduct(product: ProductEntity) {
        productDao.deleteProduct(product)
    }

    fun getProductsPaged(): PagingSource<Int, ProductEntity> {
        return productDao.getProductsPaged();
    }

}
