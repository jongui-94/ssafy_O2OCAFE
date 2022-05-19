package com.ssafy.smartstore.ui.root.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.smartstore.data.entitiy.Notification
import com.ssafy.smartstore.data.repository.Repository
import kotlinx.coroutines.launch

class NotificationViewModel: ViewModel() {

    private val repository = Repository.get()

    val notifications = repository.getNotifications()

    fun deleteNotification(notification: Notification) {
        viewModelScope.launch {
            repository.deleteNotification(notification)
        }
    }
}