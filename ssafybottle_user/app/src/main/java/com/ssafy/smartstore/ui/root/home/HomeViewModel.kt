package com.ssafy.smartstore.ui.root.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.smartstore.application.SmartStoreApplication
import com.ssafy.smartstore.base.BaseViewModel
import com.ssafy.smartstore.data.dto.order.OrderByOrderIdDto
import com.ssafy.smartstore.data.dto.order.OrderByUserDto
import com.ssafy.smartstore.data.entitiy.*
import com.ssafy.smartstore.data.repository.Repository
import kotlinx.coroutines.launch

class HomeViewModel : BaseViewModel() {
    private val repository = Repository.get()

    private val _recentOrders = MutableLiveData<List<OrderByUserDto>>()
    val recentOrders: LiveData<List<OrderByUserDto>> get() = _recentOrders

    val isSuccess = MutableLiveData<Boolean>()

    fun getRecentOrders(userId: String) {
        viewModelScope.launch(exceptionHandler) {
            repository.getOrderByUser(userId).let {
                if(it.isSuccessful) {
                    _recentOrders.postValue(it.body())
                }
            }
        }
    }
    fun insertRecentOrder(orderId : Int, userId: String) {
        viewModelScope.launch(exceptionHandler) {
            var orderList = emptyList<OrderByOrderIdDto>()
            launch(exceptionHandler) {
                repository.getOrderByOrderId(orderId).let {
                    if(it.isSuccessful) {
                        orderList = it.body()!!
                    } else {
                        isSuccess.postValue(false)
                    }
                }
            }.join()

            if(orderList.isEmpty()) {
                isSuccess.postValue(false)
                return@launch
            }

            var result = 0L
            var order = Order(userId = userId, orderTable = "")
            // Order 생성
            launch {
                repository.insertOrder(order).let {
                    result = it
                }
            }.join()

            if(result < 1) {
                isSuccess.postValue(false)
                return@launch
            }

            launch {
                repository.getLastOrderId().let {
                    SmartStoreApplication.orderId = it
                    SmartStoreApplication.isOrder = true
                }
            }.join()

            val orderDetails : List<OrderDetail> = orderList.map {
                OrderDetail(
                    orderId = SmartStoreApplication.orderId,
                    productId = it.p_id,
                    quantity = it.quantity
                )
            }

            var res = 0L
            orderDetails.forEach {
                repository.insertOrderDetail(it).let { t ->
                    res += t
                }
            }
            if(res > 0) {
                isSuccess.postValue(true)
            } else {
                isSuccess.postValue(false)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("HomeViewModel_싸피", "onCleared: ")
    }
}