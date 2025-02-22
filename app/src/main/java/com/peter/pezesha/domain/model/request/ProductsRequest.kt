package com.peter.pezesha.domain.model.request


data class ProductsRequest(
    val skip: Int,
    val limit: Int,
    val statement: String,
    val category: String? = null,
)
