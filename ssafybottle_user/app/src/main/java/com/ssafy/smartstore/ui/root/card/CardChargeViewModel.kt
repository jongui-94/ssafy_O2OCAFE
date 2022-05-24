package com.ssafy.smartstore.ui.root.card

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.smartstore.base.BaseViewModel
import com.ssafy.smartstore.data.dto.card.CardDto
import com.ssafy.smartstore.data.repository.Repository
import kotlinx.coroutines.launch

class CardChargeViewModel : BaseViewModel() {
    private val repository = Repository.get()

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> get() = _isSuccess

    var price = MutableLiveData(5000)

    fun chargeCard(card: CardDto) {
        viewModelScope.launch(exceptionHandler) {
            repository.postcard(card).let {
                if (it.isSuccessful) {
                    if (it.body() == true) {
                        _isSuccess.postValue(true)
                    } else {
                        _isSuccess.postValue(false)
                    }
                } else {
                    _isSuccess.postValue(false)
                }
            }
        }
    }
}