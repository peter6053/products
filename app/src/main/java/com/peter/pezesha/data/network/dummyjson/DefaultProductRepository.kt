package com.peter.pezesha.data.network.dummyjson

import android.util.Base64
import android.util.Log
import com.peter.pezesha.data.mapper.ProductsMapper
import com.peter.pezesha.data.network.base.BaseRepository
import com.peter.pezesha.data.network.dummyjson.datasource.DummyJsonApi
import com.peter.pezesha.data.network.dummyjson.datasource.request.EncryptedRequest
import com.peter.pezesha.domain.model.response.Product
import com.peter.pezesha.domain.model.response.ProductList
import com.peter.pezesha.domain.repository.ProductRepository
import com.peter.pezesha.utils.crypto.CryptoManager
import com.peter.pezesha.utils.platform.NetworkHandler
import java.io.ByteArrayOutputStream
import javax.inject.Inject


class DefaultProductRepository @Inject constructor(
    networkHandler: NetworkHandler,
    private val dummyJsonApi: DummyJsonApi,
    private val productsMapper: ProductsMapper,
    private val cryptoManager: CryptoManager,

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

    override suspend fun sendEncryptedData(data: String): Result<EncryptedRequest> {
        return try {
            val outputStream = ByteArrayOutputStream()
            val encryptedBytes = cryptoManager.encrypt(
                bytes = data.toByteArray(),
                outputStream = outputStream
            )

            val encryptedBase64 = Base64.encodeToString(encryptedBytes, Base64.DEFAULT)
            val request = EncryptedRequest(encryptedBase64)

            val response = dummyJsonApi.sendEncryptedData(request)

            Result.success(response)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }


}
