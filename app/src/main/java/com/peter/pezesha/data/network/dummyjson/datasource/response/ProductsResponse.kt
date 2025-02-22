package com.peter.pezesha.data.network.dummyjson.datasource.response

import kotlinx.serialization.Serializable


@Serializable
data class ProductsResponse(
    val products: List<ProductResponse>,
    val total: Int,
    val skip: Int,
    val limit: Int,
)

@Serializable
data class ProductResponse(
    val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val discountPercentage: Double,
    val rating: Double,
    val stock: Int,
    val category: String,
    val thumbnail: String,
    val images: List<String>,
)

