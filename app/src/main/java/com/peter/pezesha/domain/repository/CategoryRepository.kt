package com.peter.pezesha.domain.repository

import com.peter.pezesha.domain.model.response.CategoryList


interface CategoryRepository {

    fun getCategories(): Result<CategoryList>
}
