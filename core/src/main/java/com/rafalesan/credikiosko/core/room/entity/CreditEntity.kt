package com.rafalesan.credikiosko.core.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "credits",
    indices = [
        Index(value = ["businessId"]),
        Index(value = ["customerId"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = BusinessEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("businessId")
        ),
        ForeignKey(
            entity = CustomerEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("customerId")
        )
    ]
)
data class CreditEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val businessId: Long,
    val customerId: Long,
    val date: String,
    val total: String
)
