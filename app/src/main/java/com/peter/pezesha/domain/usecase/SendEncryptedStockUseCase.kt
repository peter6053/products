package com.peter.pezesha.domain.usecase

import com.peter.pezesha.data.network.dummyjson.datasource.request.EncryptedRequest
import com.peter.pezesha.domain.repository.ProductRepository
import javax.inject.Inject

class SendEncryptedStockUseCase @Inject constructor(
    private val productRepository: ProductRepository,
) {

    suspend operator fun invoke(stock: Int): Result<EncryptedRequest> {
        val stockString = stock.toString()
        return productRepository.sendEncryptedData(stockString)
    }
}