package com.ssafy.smartstore.ui.root.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.smartstore.base.BaseViewModel
import com.ssafy.smartstore.data.dto.order.OrderByUserDto
import com.ssafy.smartstore.data.dto.product.ProductDto
import com.ssafy.smartstore.data.dto.user.UserInfoResponseDto
import com.ssafy.smartstore.data.repository.Repository
import kotlinx.coroutines.launch

class HomeViewModel : BaseViewModel() {
    private val repository = Repository.get()

    private val _recentOrders = MutableLiveData<List<OrderByUserDto>>()
    val recentOrders: LiveData<List<OrderByUserDto>> get() = _recentOrders

    private val _user = MutableLiveData<UserInfoResponseDto>()
    val user: LiveData<UserInfoResponseDto> get() = _user

    private val _products = MutableLiveData<List<ProductDto>>()
    val products: LiveData<List<ProductDto>> get() = _products

    fun getRecentOrders(userId: String) {
        viewModelScope.launch(exceptionHandler) {
            repository.getOrderByUser(userId).let {
                if (it.isSuccessful) {
                    _recentOrders.postValue(it.body())
                }
            }
        }
    }

    fun getProducts() {
        viewModelScope.launch(exceptionHandler) {
            repository.getProducts().let {
                if (it.isSuccessful) {
                    _products.postValue(it.body())
                }
            }
        }
    }

    fun getUser(id: String) {
        viewModelScope.launch(exceptionHandler) {
            repository.getUserInfo(id).let { response ->
                if (response.isSuccessful) {
                    response.body()?.let {
                        _user.postValue(it)
                        return@launch
                    }
                }
            }
        }
    }
}