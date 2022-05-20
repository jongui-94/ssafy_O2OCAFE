package com.ssafy.smartstore.ui.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.smartstore.base.BaseViewModel
import com.ssafy.smartstore.data.dto.order.OrderByOrderIdDto
import com.ssafy.smartstore.data.repository.Repository
import kotlinx.coroutines.launch

class OrderDetailViewModel :BaseViewModel() {
    private val repository  = Repository.get()

    private val _orderDetailProducts = MutableLiveData<List<OrderByOrderIdDto>>()
    val orderDetailProducts : LiveData<List<OrderByOrderIdDto>> get() = _orderDetailProducts

    fun getOrderDetails(orderId: Int) {
        viewModelScope.launch(exceptionHandler) {
            repository.getOrderByOrderId(orderId).let {
                if(it.isSuccessful) {
                    _orderDetailProducts.postValue(it.body())
                }
            }
        }
    }
}