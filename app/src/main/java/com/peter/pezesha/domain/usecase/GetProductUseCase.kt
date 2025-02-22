package com.peter.pezesha.domain.usecase


import com.peter.pezesha.domain.model.response.Product
import com.peter.pezesha.domain.repository.ProductRepository
import com.peter.pezesha.domain.usecase.base.BaseUseCase
import javax.inject.Inject


class GetProductUseCase @Inject constructor(
    private val productRepository: ProductRepository,
) : BaseUseCase<Int, Product>() {

    override fun run(params: Int): Result<Product> =
        productRepository.getProduct(id = params)
}
