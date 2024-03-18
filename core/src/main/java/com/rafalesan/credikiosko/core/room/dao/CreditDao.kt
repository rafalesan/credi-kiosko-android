package com.rafalesan.credikiosko.core.room.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.rafalesan.credikiosko.core.room.entity.CreditEntity
import com.rafalesan.credikiosko.core.room.entity.CreditProductEntity
import com.rafalesan.credikiosko.core.room.entity.CreditWithCustomerAndProducts

@Dao
interface CreditDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCredit(credit: CreditEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCreditProduct(creditProduct: CreditProductEntity)

    @Transaction
    @Query("SELECT * FROM credits")
    fun getCreditsWithCustomerAndProductsPaged(): PagingSource<Int, CreditWithCustomerAndProducts>

    @Delete
    suspend fun deleteCredit(credit: CreditEntity)

}
