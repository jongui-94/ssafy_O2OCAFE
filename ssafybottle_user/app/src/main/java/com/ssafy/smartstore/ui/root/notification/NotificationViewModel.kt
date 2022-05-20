package com.ssafy.smartstore.ui.root.notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.smartstore.data.entitiy.Notification
import com.ssafy.smartstore.data.repository.Repository
import kotlinx.coroutines.launch

class NotificationViewModel: ViewModel() {

    private val repository = Repository.get()

    private val _notifications = MutableLiveData<List<Notification>>()
    val notifications : LiveData<List<Notification>> get() = _notifications

    var isSuccess = MutableLiveData<Boolean>()

    fun getNotifications(userId: String) {
        viewModelScope.launch {
            repository.getNotifications(userId).let {
                _notifications.postValue(it)
            }
        }
    }

    fun deleteAll(userId: String) {
        viewModelScope.launch {
            repository.deleteAllNotifications(userId).let {
                if(it > 0) {
                    isSuccess.postValue(true)
                }
            }
        }
    }

    fun deleteNotification(notification: Notification) {
        viewModelScope.launch {
            repository.deleteNotification(notification).let {
                if(it > 0) {
                    isSuccess.postValue(true)
                }
            }
        }
    }
}