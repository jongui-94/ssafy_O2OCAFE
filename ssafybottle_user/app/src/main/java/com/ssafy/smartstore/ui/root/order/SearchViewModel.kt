package com.ssafy.smartstore.ui.root.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.smartstore.base.BaseViewModel
import com.ssafy.smartstore.data.dto.product.ProductDto
import com.ssafy.smartstore.data.repository.Repository
import kotlinx.coroutines.launch

class SearchViewModel : BaseViewModel() {
    private val repository : Repository = Repository.get()

    private val _product = MutableLiveData<List<ProductDto>>()
    val product : LiveData<List<ProductDto>> get() = _product

    val isSuccess = MutableLiveData<Boolean>()

    fun searchProducts(name: String) {
        viewModelScope.launch(exceptionHandler) {
            repository.getBeverage().let{
                if(it.isSuccessful) {
                } else {
                }
            }
        }
    }
}