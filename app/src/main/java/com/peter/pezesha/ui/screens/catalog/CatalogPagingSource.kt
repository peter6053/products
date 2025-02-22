package com.peter.pezesha.ui.screens.catalog

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.peter.pezesha.domain.model.request.ProductsRequest
import com.peter.pezesha.domain.model.response.Product
import com.peter.pezesha.domain.model.response.ProductList
import com.peter.pezesha.domain.usecase.base.ProductListSource


class CatalogPagingSource(
    private val productListSource: ProductListSource,
    private val searchStatement: String,
    private val category: String?,
) : PagingSource<Int, Product>() {

    override fun getRefreshKey(state: PagingState<Int, Product>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        val currentPage = params.key ?: START_PAGE

        return productListSource(
            params = ProductsRequest(
                skip = currentPage * PAGING_PAGE_SIZE,
                limit = PAGING_PAGE_SIZE,
                statement = searchStatement,
                category = category,
            )
        ).fold(
            onSuccess = { modelToPage(currentPage = currentPage, model = it) },
            onFailure = { LoadResult.Error(throwable = it) }
        )
    }

    private fun modelToPage(currentPage: Int, model: ProductList): LoadResult.Page<Int, Product> {
        val prevPage = currentPage - ADD_PAGE
        val nextPage = currentPage + ADD_PAGE
        val totalPages = model.totalProducts / PAGING_PAGE_SIZE

        val itemsBefore = if (currentPage == 0) 0 else (currentPage - ADD_PAGE) * PAGING_PAGE_SIZE
        val itemsAfter =
            if (nextPage == totalPages) model.products.size else (totalPages - currentPage) * PAGING_PAGE_SIZE

        return LoadResult.Page(
            data = model.products,
            prevKey = if (prevPage >= 0) prevPage else null,
            nextKey = if (nextPage <= totalPages) nextPage else null,
            itemsBefore = itemsBefore,
            itemsAfter = itemsAfter,
        )
    }

    companion object {
        private const val START_PAGE = 0
        private const val ADD_PAGE = 1
        const val PAGING_PAGE_SIZE = 20
        const val PAGING_MAX_SIZE = 80
    }
}
