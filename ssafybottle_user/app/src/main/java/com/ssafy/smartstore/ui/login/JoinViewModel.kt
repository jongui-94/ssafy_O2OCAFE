package com.ssafy.smartstore.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.smartstore.base.BaseViewModel
import com.ssafy.smartstore.data.dto.user.UserDto
import com.ssafy.smartstore.data.repository.Repository
import kotlinx.coroutines.launch

class JoinViewModel : BaseViewModel() {
    private val repository : Repository = Repository.get()

    private val _isComplete = MutableLiveData<Boolean>()
    val isComplete : LiveData<Boolean> get() = _isComplete

    private val _isOverlap = MutableLiveData<Boolean>()
    val isOverlap : LiveData<Boolean> get() = _isOverlap

    fun insertUser(user: UserDto) {
        viewModelScope.launch(exceptionHandler) {
            var result = false

            launch(exceptionHandler) {
                repository.checkUserId(user.id).let { response ->
                    if(response.isSuccessful) {
                        // 중복 아이디 있을때
                        if(response.body()!!) {
                            result = true
                            _isOverlap.postValue(true)
                        }
                    }
                }
            }.join()

            // 중복 아이디 없을때
            if(!result) {
                repository.insertUser(user).let { response ->
                    if(response.isSuccessful) {
                        _isComplete.postValue(true)
                    } else {
                        _isComplete.postValue(false)
                    }
                }
            }
        }
    }
}