package com.rafalesan.credikiosko.core.di

import android.content.Context
import androidx.room.Room
import com.rafalesan.credikiosko.core.room.AppDatabase
import com.rafalesan.credikiosko.core.room.dao.BusinessDao
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
        ).build();
    }

    @Provides
    fun provideBusinessDao(appDatabase: AppDatabase): BusinessDao {
        return appDatabase.businessDao()
    }

}
