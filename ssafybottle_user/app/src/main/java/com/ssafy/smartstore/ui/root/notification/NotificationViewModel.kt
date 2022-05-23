package com.ssafy.smartstore.ui.root.notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.smartstore.base.BaseViewModel
import com.ssafy.smartstore.data.dto.notification.NotificationDto
import com.ssafy.smartstore.data.repository.Repository
import kotlinx.coroutines.launch

class NotificationViewModel : BaseViewModel() {

    private val repository = Repository.get()

    private val _notifications = MutableLiveData<List<NotificationDto>>()
    val notifications: LiveData<List<NotificationDto>> get() = _notifications

    var isSuccess = MutableLiveData<Boolean>()

    fun getNotifications(userId: String) {
        viewModelScope.launch(exceptionHandler) {
            repository.getNotifications(userId).let {
                if (it.isSuccessful) {
                    _notifications.postValue(it.body())
                }
            }
        }
    }

    fun deleteNotification(notificationId: Int) {
        viewModelScope.launch(exceptionHandler) {
            repository.deleteNotification(notificationId).let {
                if (it.isSuccessful) {
                    if (it.body()!! > 0) {
                        isSuccess.postValue(true)
                    }
                }
            }
        }
    }

    fun deleteAllNotification(userId: String) {
        viewModelScope.launch(exceptionHandler) {
            repository.deleteAllNotification(userId).let {
                if (it.isSuccessful) {
                    if (it.body()!! > 0) {
                        isSuccess.postValue(true)
                    }
                }
            }
        }
    }
}