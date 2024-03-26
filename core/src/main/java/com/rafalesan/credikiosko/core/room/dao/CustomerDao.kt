package com.rafalesan.credikiosko.core.room.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rafalesan.credikiosko.core.room.entity.CustomerEntity

@Dao
interface CustomerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomer(customer: CustomerEntity)

    @Query("SELECT * FROM customers ORDER BY id DESC")
    fun getCustomerPaged(): PagingSource<Int, CustomerEntity>

    @Delete
    suspend fun deleteCustomer(customer: CustomerEntity)

}
