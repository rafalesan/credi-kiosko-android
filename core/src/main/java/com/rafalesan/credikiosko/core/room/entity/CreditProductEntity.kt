package com.rafalesan.credikiosko.core.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "credit_product",
    foreignKeys = [
        ForeignKey(
            entity = CreditEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("creditId"),
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ProductEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("productId")
        )
    ]
)
data class CreditProductEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val creditId: Long,
    val productId: Long,
    val productName: String,
    val productPrice: String,
    val quantity: String,
    val total: String
)
