package com.rafalesan.credikiosko.core.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rafalesan.credikiosko.core.room.dao.BusinessDao
import com.rafalesan.credikiosko.core.room.entity.BusinessEntity

@Database(
    version = 1,
    entities = [
        BusinessEntity::class
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun businessDao(): BusinessDao

}