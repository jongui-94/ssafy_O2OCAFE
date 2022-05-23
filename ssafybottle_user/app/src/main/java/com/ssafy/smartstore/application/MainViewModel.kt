package com.ssafy.smartstore.application

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.smartstore.base.BaseViewModel
import com.ssafy.smartstore.data.dto.card.CardDto
import com.ssafy.smartstore.data.dto.order.OrderDetailDto
import com.ssafy.smartstore.data.dto.order.OrderRequestDto
import com.ssafy.smartstore.data.repository.Repository
import kotlinx.coroutines.launch

class MainViewModel : BaseViewModel() {
    private val repository = Repository.get()

    var orderList: MutableList<OrderDetailDto> = mutableListOf()

    var isComplete = MutableLiveData<Boolean>()

    var lackOfBalance = MutableLiveData<Boolean>()

    var coupon = MutableLiveData<Int>()

    private var cash: Int = 0

    fun postOrder(orderRequestDto: OrderRequestDto) {
        viewModelScope.launch(exceptionHandler) {

            var totalCost = 0
            orderRequestDto.details.forEach {
                totalCost += it.quantity * it.price
            }

            if (totalCost > cash) {
                lackOfBalance.postValue(true)
                return@launch
            }

            repository.postcard(
                CardDto(
                    content = "테이크 아웃",
                    orderId = -1,
                    payment = -totalCost,
                    userId = orderRequestDto.userId,
                    payTime = "",
                    id = 0
                )
            )

            repository.postOrder(orderRequestDto).let {
                if (it.isSuccessful) {
                    isComplete.postValue(true)
                } else {
                    isComplete.postValue(false)
                }
            }
        }
    }

    fun checkCash(userId: String) {
        viewModelScope.launch(exceptionHandler) {
            repository.checkCash(userId).let {
                if (it.isSuccessful) {
                    cash = it.body()!!
                }
            }
        }
    }
}