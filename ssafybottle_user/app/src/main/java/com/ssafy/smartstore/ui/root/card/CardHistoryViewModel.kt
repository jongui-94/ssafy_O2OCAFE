package com.ssafy.smartstore.ui.root.card

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.smartstore.base.BaseViewModel
import com.ssafy.smartstore.data.dto.card.CardDto
import com.ssafy.smartstore.data.dto.order.OrderByUserDto
import com.ssafy.smartstore.data.repository.Repository
import kotlinx.coroutines.launch

class CardHistoryViewModel : BaseViewModel() {
    private val repository = Repository.get()

    private val _cardList = MutableLiveData<List<CardDto>>()
    val cardList: LiveData<List<CardDto>> get() = _cardList

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess : LiveData<Boolean> get() = _isSuccess

    fun getCardHistory(userId: String) {
        viewModelScope.launch(exceptionHandler) {
            repository.getCardHistory(userId).let{
                if(it.isSuccessful) {
                    _cardList.postValue(it.body())
                } else {
                    _isSuccess.postValue(false)
                }
            }
        }
    }


}