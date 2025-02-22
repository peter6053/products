package com.peter.pezesha.domain.usecase.base

import com.peter.pezesha.domain.model.request.ProductsRequest
import com.peter.pezesha.domain.model.response.ProductList


interface ProductListSource {

    operator fun invoke(params: ProductsRequest): Result<ProductList>
}
