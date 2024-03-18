package com.rafalesan.credikiosko.core.di

import android.content.Context
import androidx.room.Room
import com.rafalesan.credikiosko.core.room.AppDatabase
import com.rafalesan.credikiosko.core.room.dao.BusinessDao
import com.rafalesan.credikiosko.core.room.dao.CreditDao
import com.rafalesan.credikiosko.core.room.dao.CustomerDao
import com.rafalesan.credikiosko.core.room.dao.ProductDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RoomDatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext appContext: Context
    ) : AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "AppDatabase"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideBusinessDao(appDatabase: AppDatabase): BusinessDao {
        return appDatabase.businessDao()
    }

    @Provides
    fun provideProductDao(appDatabase: AppDatabase): ProductDao {
        return appDatabase.productDao()
    }

    @Provides
    fun provideCustomerDao(appDatabase: AppDatabase): CustomerDao {
        return appDatabase.customerDao()
    }

    @Provides
    fun provideCreditDao(appDatabase: AppDatabase): CreditDao {
        return appDatabase.creditDao()
    }

}
