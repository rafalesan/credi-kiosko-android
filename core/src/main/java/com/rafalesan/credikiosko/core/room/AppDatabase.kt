package com.rafalesan.credikiosko.core.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rafalesan.credikiosko.core.room.dao.BusinessDao
import com.rafalesan.credikiosko.core.room.dao.CustomerDao
import com.rafalesan.credikiosko.core.room.dao.ProductDao
import com.rafalesan.credikiosko.core.room.entity.BusinessEntity
import com.rafalesan.credikiosko.core.room.entity.CustomerEntity
import com.rafalesan.credikiosko.core.room.entity.ProductEntity

@Database(
    version = 1,
    entities = [
        BusinessEntity::class,
        ProductEntity::class,
        CustomerEntity::class
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun businessDao(): BusinessDao
    abstract fun productDao(): ProductDao
    abstract fun customerDao(): CustomerDao

}