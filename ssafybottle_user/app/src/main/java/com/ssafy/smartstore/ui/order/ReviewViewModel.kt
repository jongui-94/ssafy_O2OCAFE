package com.ssafy.smartstore.ui.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.smartstore.base.BaseViewModel
import com.ssafy.smartstore.data.dto.comment.CommentDto
import com.ssafy.smartstore.data.repository.Repository
import kotlinx.coroutines.launch

class ReviewViewModel : BaseViewModel() {
    private val repository = Repository.get()

    private val _isPosted = MutableLiveData<Boolean>()
    val isPosted: LiveData<Boolean> get() = _isPosted

    private val _isUpdated = MutableLiveData<Boolean>()
    val isUpdated: LiveData<Boolean> get() = _isUpdated

    fun updateComment(comment : CommentDto) {
        viewModelScope.launch(exceptionHandler) {
            repository.updateComment(comment).let {
                if(it.isSuccessful) {
                    _isUpdated.postValue(true)
                } else {
                    _isUpdated.postValue(false)
                }
            }
        }
    }

    fun insertComment(comment: CommentDto) {
        viewModelScope.launch(exceptionHandler) {
            repository.insertComment(comment).let {
                if(it.isSuccessful) {
                    _isPosted.postValue(true)
                } else {
                    _isUpdated.postValue(false)
                }
            }
        }
    }
}