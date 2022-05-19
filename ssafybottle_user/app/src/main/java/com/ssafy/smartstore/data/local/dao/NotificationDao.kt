package com.ssafy.smartstore.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ssafy.smartstore.data.entitiy.Notification

@Dao
interface NotificationDao {
    @Query("SELECT * FROM t_notification")
    fun getNotifications() : LiveData<List<Notification>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notification: Notification)

    @Delete
    suspend fun deleteNotification(notification: Notification)
}