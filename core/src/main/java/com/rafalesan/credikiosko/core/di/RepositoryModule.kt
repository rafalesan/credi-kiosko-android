package com.rafalesan.credikiosko.core.di

import com.rafalesan.credikiosko.core.bluetooth_printer.data.datasource.BluetoothDataSource
import com.rafalesan.credikiosko.core.bluetooth_printer.data.datasource.BluetoothPrinterLocalDataSource
import com.rafalesan.credikiosko.core.bluetooth_printer.data.repository.BluetoothDeviceRepository
import com.rafalesan.credikiosko.core.bluetooth_printer.data.repository.BluetoothPrinterRepository
import com.rafalesan.credikiosko.core.bluetooth_printer.domain.repository.IBluetoothDeviceRepository
import com.rafalesan.credikiosko.core.bluetooth_printer.domain.repository.IBluetoothPrinterRepository
import com.rafalesan.credikiosko.core.commons.data.datasource.local.BusinessLocalDataSource
import com.rafalesan.credikiosko.core.commons.data.repository.BusinessRepository
import com.rafalesan.credikiosko.core.commons.domain.repository.IBusinessRepository
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
    fun provideProductRepository(
        businessLocalDataSource: BusinessLocalDataSource
    ): IBusinessRepository {
        return BusinessRepository(businessLocalDataSource)
    }

    @Provides
    @Singleton
    fun provideBluetoothPrinterRepository(
        bluetoothPrinterLocalDataSource: BluetoothPrinterLocalDataSource
    ): IBluetoothPrinterRepository {
        return BluetoothPrinterRepository(bluetoothPrinterLocalDataSource)
    }

    @Provides
    @Singleton
    fun provideBluetoothDeviceRepository(
        bluetoothDataSource: BluetoothDataSource
    ): IBluetoothDeviceRepository {
        return BluetoothDeviceRepository(bluetoothDataSource)
    }


}