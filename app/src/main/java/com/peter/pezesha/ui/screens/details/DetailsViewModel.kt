package com.peter.pezesha.ui.screens.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.peter.pezesha.domain.model.response.Product
import com.peter.pezesha.domain.usecase.GetProductUseCase
import com.peter.pezesha.ui.base.BaseViewModel

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel


@HiltViewModel(assistedFactory = DetailsViewModel.Factory::class)
class DetailsViewModel @AssistedInject constructor(
    @Assisted private val productId: Int,
    private val getProductUseCase: GetProductUseCase,
) : BaseViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(productId: Int): DetailsViewModel
    }

    init {
        getProduct()
    }

    private val _product: MutableLiveData<Product> = MutableLiveData()
    val product: LiveData<Product> = _product

    fun getProduct() {
        _exception.value = null
        _loading.value = true

        getProductUseCase(
            params = productId,
            scope = viewModelScope,
        ) { result ->
            result.fold(
                onSuccess = {
                    _product.value = it
                    _loading.value = false
                },
                onFailure = ::handleException,
            )
        }
    }
}
