package com.ssafy.smartstore.application

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.smartstore.base.BaseViewModel
import com.ssafy.smartstore.data.dto.order.OrderDetailDto
import com.ssafy.smartstore.data.dto.order.OrderRequestDto
import com.ssafy.smartstore.data.repository.Repository
import kotlinx.coroutines.launch

class MainViewModel : BaseViewModel() {
    private val repository = Repository.get()

    var orderList: MutableList<OrderDetailDto> = mutableListOf()

    var isComplete = MutableLiveData<Boolean>()

    fun postOrder(orderRequestDto: OrderRequestDto) {
        viewModelScope.launch(exceptionHandler) {
            repository.postOrder(orderRequestDto).let {
                if (it.isSuccessful) {
                    isComplete.postValue(true)
                } else {
                    isComplete.postValue(false)
                }
            }
        }
    }
}