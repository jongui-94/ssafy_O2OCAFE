package com.ssafy.smartstore.ui.root.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.smartstore.base.BaseViewModel
import com.ssafy.smartstore.data.dto.product.ProductDto
import com.ssafy.smartstore.data.repository.Repository
import kotlinx.coroutines.launch

class MenuDetailViewModel : BaseViewModel() {
    private val repository : Repository = Repository.get()

    private val _beverage = MutableLiveData<List<ProductDto>>()
    val beverage : LiveData<List<ProductDto>> get() = _beverage

    private val _dessert = MutableLiveData<List<ProductDto>>()
    val dessert : LiveData<List<ProductDto>> get() = _dessert

    private val _top10 = MutableLiveData<List<ProductDto>>()
    val top10 : LiveData<List<ProductDto>> get() = _top10

    val isSuccess = MutableLiveData<Boolean>()


    fun getProducts() {
        viewModelScope.launch(exceptionHandler) {
            repository.getBeverage().let{
                if(it.isSuccessful) {
                    _beverage.postValue(it.body())
                } else {
                    isSuccess.postValue(false)
                }
            }
            repository.getDessert().let{
                if(it.isSuccessful) {
                    _dessert.postValue(it.body())
                } else {
                    isSuccess.postValue(false)
                }
            }
            repository.getProducts().let{
                if(it.isSuccessful) {
                    _top10.postValue(it.body())
                } else {
                    isSuccess.postValue(false)
                }
            }
        }
    }
}