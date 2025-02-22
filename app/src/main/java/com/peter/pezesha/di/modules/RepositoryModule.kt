package com.peter.pezesha.di.modules


import com.peter.pezesha.data.network.dummyjson.DefaultCategoryRepository
import com.peter.pezesha.data.network.dummyjson.DefaultProductRepository
import com.peter.pezesha.domain.repository.CategoryRepository
import com.peter.pezesha.domain.repository.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindCategoryRepository(
        categoryRepository: DefaultCategoryRepository,
    ): CategoryRepository

    @Binds
    abstract fun bindProductRepository(
        productRepository: DefaultProductRepository,
    ): ProductRepository
}
