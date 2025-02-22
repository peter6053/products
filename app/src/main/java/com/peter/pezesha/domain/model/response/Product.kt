package com.peter.pezesha.domain.model.response


data class Product(
    val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val priceWithoutDiscount: Int,
    val discountPercentage: Int,
    val rating: Float,
    val stock: Int,
    val category: String,
    val thumbnailUrl: String,
    val imageUrls: List<String>,
)
