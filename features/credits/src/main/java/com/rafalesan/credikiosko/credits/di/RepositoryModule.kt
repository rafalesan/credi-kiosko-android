package com.rafalesan.credikiosko.credits.di

import com.rafalesan.credikiosko.core.bluetooth_printer.data.datasource.ThermalPrinterDataSource
import com.rafalesan.credikiosko.core.commons.data.datasource.local.BusinessLocalDataSource
import com.rafalesan.credikiosko.credits.data.datasource.CreditLocalDataSource
import com.rafalesan.credikiosko.credits.data.repository.CreditRepository
import com.rafalesan.credikiosko.credits.domain.repository.ICreditRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideCreditRepository(
        creditLocalDataSource: CreditLocalDataSource,
        businessLocalDataSource: BusinessLocalDataSource,
        thermalPrinterDataSource: ThermalPrinterDataSource,
    ): ICreditRepository {
        return CreditRepository(
            creditLocalDataSource,
            businessLocalDataSource,
            thermalPrinterDataSource
        )
    }

}
