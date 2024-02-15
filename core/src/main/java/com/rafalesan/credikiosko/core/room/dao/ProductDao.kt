package com.rafalesan.credikiosko.core.room.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rafalesan.credikiosko.core.room.entity.ProductEntity

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: ProductEntity)

    @Query("SELECT * FROM products")
    fun getProductsPaged(): PagingSource<Int, ProductEntity>

    @Delete
    suspend fun deleteProduct(product: ProductEntity)

}
