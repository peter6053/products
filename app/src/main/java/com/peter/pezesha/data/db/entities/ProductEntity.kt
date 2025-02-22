package com.peter.pezesha.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val discountPercentage: Double,
    val rating: Float,
    val stock: Int,
    val category: String,
    val imagePath: String,
    val images: String
)


