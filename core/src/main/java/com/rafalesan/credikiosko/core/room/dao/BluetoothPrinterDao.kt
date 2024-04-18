package com.rafalesan.credikiosko.core.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.rafalesan.credikiosko.core.room.entity.BluetoothPrinterEntity

@Dao
interface BluetoothPrinterDao {

    @Insert
    suspend fun insertPrinter(bluetoothPrinter: BluetoothPrinterEntity)

    @Query("SELECT * FROM bluetooth_printers LIMIT 1")
    suspend fun getBluetoothPrinter(): BluetoothPrinterEntity?

    @Query("SELECT EXISTS(SELECT * FROM bluetooth_printers)")
    suspend fun existsPrinter(): Boolean

    @Query("DELETE FROM bluetooth_printers")
    suspend fun removePrinter()

}
