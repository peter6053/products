package com.peter.pezesha.ui.screens.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.peter.pezesha.domain.model.response.CategoryList
import com.peter.pezesha.domain.model.response.Product
import com.peter.pezesha.domain.usecase.GetCategoriesUseCase
import com.peter.pezesha.domain.usecase.GetProductsByCategorySyncUseCase
import com.peter.pezesha.domain.usecase.GetProductsSyncUseCase
import com.peter.pezesha.domain.usecase.base.BaseUseCase
import com.peter.pezesha.ui.base.BaseViewModel
import com.peter.pezesha.ui.screens.catalog.CatalogPagingSource.Companion.PAGING_MAX_SIZE
import com.peter.pezesha.ui.screens.catalog.CatalogPagingSource.Companion.PAGING_PAGE_SIZE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val getProductsSyncUseCase: GetProductsSyncUseCase,
    private val getProductsByCategorySyncUseCase: GetProductsByCategorySyncUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
) : BaseViewModel() {

    private val _productFlow: MutableLiveData<Flow<PagingData<Product>>> = MutableLiveData()
    val productFlow: LiveData<Flow<PagingData<Product>>> = _productFlow

    private val _categories: MutableLiveData<CategoryList> = MutableLiveData()
    val categories: LiveData<CategoryList> = _categories

    private var searchStatement: String = ""
    private var category: String? = null

    private var pager: Pager<Int, Product>? = null

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    init {
        getProductFlow()
        getCategories()
    }

    fun setSearchStatement(searchStatement: String) {
        if (searchStatement == this.searchStatement) return
        this.searchStatement = searchStatement
        getProductFlow()
    }

    fun setCategory(category: String?) {
        if (category == this.category) return
        this.category = category
        getProductFlow()
    }

    fun getProductFlow() {
        pager = Pager(
            config = PagingConfig(
                pageSize = PAGING_PAGE_SIZE,
                maxSize = PAGING_MAX_SIZE,
            ),
            pagingSourceFactory = {
                CatalogPagingSource(
                    productListSource = if (category == null) getProductsSyncUseCase
                    else getProductsByCategorySyncUseCase,
                    searchStatement = searchStatement,
                    category = category,
                )
            },
        )

        _productFlow.value = pager?.flow?.cachedIn(scope = scope)
    }

    fun getCategories() {
        getCategoriesUseCase(
            params = BaseUseCase.NoParams,
            scope = viewModelScope,
        ) { result ->
            result.fold(
                onSuccess = { _categories.value = it },
                onFailure = { _exception.value = it },
            )
        }
    }
}
