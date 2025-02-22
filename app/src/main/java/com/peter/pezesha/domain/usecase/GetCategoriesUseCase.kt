package com.peter.pezesha.domain.usecase


import com.peter.pezesha.domain.model.response.CategoryList
import com.peter.pezesha.domain.repository.CategoryRepository
import com.peter.pezesha.domain.usecase.base.BaseUseCase
import javax.inject.Inject


class GetCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository,
) : BaseUseCase<BaseUseCase.NoParams, CategoryList>() {

    override fun run(params: NoParams): Result<CategoryList> =
        categoryRepository.getCategories()
}
