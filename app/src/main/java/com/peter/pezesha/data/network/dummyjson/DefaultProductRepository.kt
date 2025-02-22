package com.peter.pezesha.data.network.dummyjson

import com.peter.pezesha.data.mapper.ProductsMapper
import com.peter.pezesha.data.network.base.BaseRepository
import com.peter.pezesha.data.network.dummyjson.datasource.DummyJsonApi
import com.peter.pezesha.domain.model.response.Product
import com.peter.pezesha.domain.model.response.ProductList
import com.peter.pezesha.domain.repository.ProductRepository
import com.peter.pezesha.utils.platform.NetworkHandler
import javax.inject.Inject


class DefaultProductRepository @Inject constructor(
    networkHandler: NetworkHandler,
    private val dummyJsonApi: DummyJsonApi,
    private val productsMapper: ProductsMapper,
) : BaseRepository(networkHandler = networkHandler), ProductRepository {

    override fun getProduct(id: Int): Result<Product> =
        request(
            call = dummyJsonApi.getProduct(id = id),
            transform = { productResponse -> productsMapper.toModel(productResponse = productResponse) },
        )

    override fun getProducts(skip: Int, limit: Int, statement: String): Result<ProductList> =
        request(
            call = dummyJsonApi.getProducts(skip = skip, limit = limit, statement = statement),
            transform = { productsResponse -> productsMapper.toModel(productsResponse = productsResponse) },
        )

    override fun getProductsByCategory(category: String): Result<ProductList> =
        request(
            call = dummyJsonApi.getProductsByCategory(category = category),
            transform = { productsResponse -> productsMapper.toModel(productsResponse = productsResponse) },
        )
}
