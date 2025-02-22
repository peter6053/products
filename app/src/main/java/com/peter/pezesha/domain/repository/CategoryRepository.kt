package com.peter.pezesha.domain.repository

import com.peter.pezesha.data.network.dummyjson.datasource.request.EncryptedRequest
import com.peter.pezesha.domain.model.response.CategoryList


interface CategoryRepository {
    fun getCategories(): Result<CategoryList>
    suspend fun sendEncryptedData(data: String): Result<EncryptedRequest>
}
