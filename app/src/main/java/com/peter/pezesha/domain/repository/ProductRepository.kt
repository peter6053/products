package com.peter.pezesha.domain.repository

import com.peter.pezesha.data.network.dummyjson.datasource.request.EncryptedRequest
import com.peter.pezesha.domain.model.response.Product
import com.peter.pezesha.domain.model.response.ProductList


interface ProductRepository {

    fun getProduct(id: Int): Result<Product>

    fun getProducts(skip: Int, limit: Int, statement: String): Result<ProductList>

    fun getProductsByCategory(category: String): Result<ProductList>
    suspend fun sendEncryptedData(data: String): Result<EncryptedRequest>
}
