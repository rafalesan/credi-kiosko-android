package com.rafalesan.credikiosko.core.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rafalesan.credikiosko.core.room.entity.BusinessEntity

@Dao
interface BusinessDao {

    @Query("SELECT * FROM businesses LIMIT 1")
    suspend fun getBusiness(): BusinessEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBusiness(business: BusinessEntity)

    @Query("SELECT EXISTS(SELECT * FROM businesses)")
    suspend fun existsBusiness(): Boolean

}
