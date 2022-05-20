package com.ssafy.smartstore.ui.root.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.smartstore.base.BaseViewModel
import com.ssafy.smartstore.data.dto.product.ProductDto
import com.ssafy.smartstore.data.repository.Repository
import kotlinx.coroutines.launch

class SearchViewModel : BaseViewModel() {
    private val repository: Repository = Repository.get()

    private val _products = MutableLiveData<List<ProductDto>>()
    val products: LiveData<List<ProductDto>> get() = _products

    val isSuccess = MutableLiveData<Boolean>()

    fun searchProduct(productName: String) {
        viewModelScope.launch(exceptionHandler) {
            repository.searchProduct(productName).let {
                if (it.isSuccessful) {
                    _products.postValue(it.body())
                } else {
                    isSuccess.postValue(false)
                }
            }
        }
    }
}