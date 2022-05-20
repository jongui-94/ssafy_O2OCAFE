package com.ssafy.smartstore.ui.root.card

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.smartstore.base.BaseViewModel
import com.ssafy.smartstore.data.repository.Repository
import kotlinx.coroutines.launch

class CardViewModel : BaseViewModel() {
    private val repository = Repository.get()

    private val _cash = MutableLiveData<Int>()
    val cash: LiveData<Int> get() = _cash

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> get() = _isSuccess

    fun checkCash(userId: String) {
        viewModelScope.launch(exceptionHandler) {
            repository.checkCash(userId).let {
                if (it.isSuccessful) {
                    _cash.postValue(it.body())
                } else {
                    _isSuccess.postValue(false)
                }
            }
        }
    }
}
