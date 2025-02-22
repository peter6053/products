package com.peter.pezesha.data.network.dummyjson

import android.util.Log
import com.peter.pezesha.data.mapper.CategoriesMapper
import com.peter.pezesha.data.network.base.BaseRepository
import com.peter.pezesha.data.network.dummyjson.datasource.DummyJsonApi
import com.peter.pezesha.data.network.dummyjson.datasource.request.EncryptedRequest
import com.peter.pezesha.domain.model.response.CategoryList
import com.peter.pezesha.domain.repository.CategoryRepository
import com.peter.pezesha.utils.crypto.CryptoManager
import com.peter.pezesha.utils.platform.NetworkHandler
import retrofit2.HttpException

import javax.inject.Inject
import android.util.Base64
import java.io.ByteArrayOutputStream


class DefaultCategoryRepository @Inject constructor(
    networkHandler: NetworkHandler,
    private val dummyJsonApi: DummyJsonApi,
    private val categoriesMapper: CategoriesMapper,
    private val cryptoManager: CryptoManager
) : BaseRepository(networkHandler = networkHandler), CategoryRepository {

    override fun getCategories(): Result<CategoryList> =
        request(
            call = dummyJsonApi.getCategories(),
            transform = { categoriesResponse -> categoriesMapper.toModel(categoriesResponse = categoriesResponse) },
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

            request(
                call = dummyJsonApi.sendEncryptedData(request),
                transform = { it }
            )

        } catch (e: Exception) {
            Log.e("Crypto", "Encryption/Transmission Error", e)
            Result.failure(e)
        }
    }



}
