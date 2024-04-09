package com.rafalesan.credikiosko.core.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "bluetooth_printers")
data class BluetoothPrinterEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val macAddress: String,
)
