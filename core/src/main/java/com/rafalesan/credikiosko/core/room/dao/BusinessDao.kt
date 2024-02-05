package com.rafalesan.credikiosko.core.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.rafalesan.credikiosko.core.room.entity.BusinessEntity

@Dao
interface BusinessDao {

    @Query("SELECT * FROM businesses LIMIT 1")
    suspend fun getBusiness(): BusinessEntity

    @Insert
    suspend fun insertBusiness(business: BusinessEntity)

}
