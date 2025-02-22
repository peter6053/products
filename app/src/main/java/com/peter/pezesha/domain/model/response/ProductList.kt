package com.peter.pezesha.domain.model.response


data class ProductList(
    val products: List<Product>,
    val totalProducts: Int,
    val skip: Int,
    val limit: Int,
)
