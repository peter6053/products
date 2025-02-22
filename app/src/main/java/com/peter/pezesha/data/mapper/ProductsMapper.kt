package com.peter.pezesha.data.mapper

import com.peter.pezesha.data.db.entities.ProductEntity
import com.peter.pezesha.data.network.dummyjson.datasource.response.ProductResponse
import com.peter.pezesha.data.network.dummyjson.datasource.response.ProductsResponse
import com.peter.pezesha.domain.model.response.Product
import com.peter.pezesha.domain.model.response.ProductList
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.roundToInt


@Singleton
class ProductsMapper @Inject constructor() {

    fun toModel(productsResponse: ProductsResponse): ProductList =
        ProductList(
            products = productsResponse.products.map { toModel(productResponse = it) },
            totalProducts = productsResponse.total,
            skip = productsResponse.skip,
            limit = productsResponse.limit,
        )

    fun toModel(productResponse: ProductResponse): Product =
        Product(
            id = productResponse.id,
            title = productResponse.title,
            description = productResponse.description,
            price = productResponse.price,
            priceWithoutDiscount = (100 * productResponse.price / (100 - productResponse.discountPercentage)).roundToInt(),
            discountPercentage = productResponse.discountPercentage.roundToInt(),
            rating = productResponse.rating.toFloat(),
            stock = productResponse.stock,
            category = productResponse.category,
            thumbnailUrl = productResponse.thumbnail,
            imageUrls = productResponse.images,
        )

    fun toEntity(productResponse: ProductResponse, imagePath: String): ProductEntity =
        ProductEntity(
            id = productResponse.id,
            title = productResponse.title,
            description = productResponse.description,
            price = productResponse.price,
            discountPercentage = productResponse.discountPercentage,
            rating = productResponse.rating.toFloat(),
            stock = productResponse.stock,
            category = productResponse.category,
            imagePath = imagePath,
            images = productResponse.images.joinToString(",")
        )

    fun fromEntity(productEntity: ProductEntity): Product =
        Product(
            id = productEntity.id,
            title = productEntity.title,
            description = productEntity.description,
            price = productEntity.price,
            priceWithoutDiscount = (100 * productEntity.price / (100 - productEntity.discountPercentage)).roundToInt(),
            discountPercentage = productEntity.discountPercentage.roundToInt(),
            rating = productEntity.rating,
            stock = productEntity.stock,
            category = productEntity.category,
            thumbnailUrl = productEntity.imagePath,
            imageUrls = productEntity.images.split(",")
        )
}

