package com.ssafy.smartstore.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ssafy.smartstore.data.entitiy.Notification

@Dao
interface NotificationDao {
    @Query("SELECT * FROM t_notification WHERE user_id = (:userId)")
    suspend fun getNotifications(userId: String) : List<Notification>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notification: Notification)

    @Delete
    suspend fun deleteNotification(notification: Notification) : Int

    @Query("DELETE FROM t_notification WHERE user_id = (:userId)")
    suspend fun deleteAllNotifications(userId: String) : Int
}