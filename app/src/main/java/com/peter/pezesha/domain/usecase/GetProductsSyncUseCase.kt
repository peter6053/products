package com.peter.pezesha.domain.usecase

import com.peter.pezesha.domain.model.request.ProductsRequest
import com.peter.pezesha.domain.model.response.ProductList
import com.peter.pezesha.domain.repository.ProductRepository
import com.peter.pezesha.domain.usecase.base.ProductListSource
import javax.inject.Inject


class GetProductsSyncUseCase @Inject constructor(
    private val productRepository: ProductRepository,
) : ProductListSource {

    override fun invoke(params: ProductsRequest): Result<ProductList> =
        productRepository.getProducts(skip = params.skip, limit = params.limit, statement = params.statement)
}
