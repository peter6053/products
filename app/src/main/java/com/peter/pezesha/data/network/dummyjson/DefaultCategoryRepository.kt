package com.peter.pezesha.data.network.dummyjson

import com.peter.pezesha.data.mapper.CategoriesMapper
import com.peter.pezesha.data.network.base.BaseRepository
import com.peter.pezesha.data.network.dummyjson.datasource.DummyJsonApi
import com.peter.pezesha.domain.model.response.CategoryList
import com.peter.pezesha.domain.repository.CategoryRepository
import com.peter.pezesha.utils.platform.NetworkHandler

import javax.inject.Inject


class DefaultCategoryRepository @Inject constructor(
    networkHandler: NetworkHandler,
    private val dummyJsonApi: DummyJsonApi,
    private val categoriesMapper: CategoriesMapper,
) : BaseRepository(networkHandler = networkHandler), CategoryRepository {

    override fun getCategories(): Result<CategoryList> =
        request(
            call = dummyJsonApi.getCategories(),
            transform = { categoriesResponse -> categoriesMapper.toModel(categoriesResponse = categoriesResponse) },
        )
}
