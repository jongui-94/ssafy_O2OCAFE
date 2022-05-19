package com.ssafy.smartstore.ui.root.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.smartstore.base.BaseViewModel
import com.ssafy.smartstore.data.dto.order.OrderByUserDto
import com.ssafy.smartstore.data.dto.user.UserInfoResponseDto
import com.ssafy.smartstore.data.repository.Repository
import kotlinx.coroutines.launch

class MyPageViewModel : BaseViewModel() {

    private val repository = Repository.get()

    private val _user = MutableLiveData<UserInfoResponseDto>()
    val user : LiveData<UserInfoResponseDto> get() = _user

    private val _orderByUserId = MutableLiveData<List<OrderByUserDto>>()
    val orderByUserId : LiveData<List<OrderByUserDto>> get() = _orderByUserId

    val isSuccess = MutableLiveData<Boolean>()

    fun getUser(id: String) {
        viewModelScope.launch(exceptionHandler) {
            repository.getUserInfo(id).let { response ->
                if(response.isSuccessful) {
                    response.body()?.let {
                        _user.postValue(it)
                        return@launch
                    }
                    isSuccess.postValue(false)
                }
            }
        }
    }

    fun getRecentOrders(userId : String) {
        viewModelScope.launch(exceptionHandler) {
            repository.getOrderByUser(userId).let {
                if(it.isSuccessful) {
                    _orderByUserId.postValue(it.body())
                }
            }
        }
    }
}