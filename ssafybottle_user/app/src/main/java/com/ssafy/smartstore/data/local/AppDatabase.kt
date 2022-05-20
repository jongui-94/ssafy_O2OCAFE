package com.ssafy.smartstore.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ssafy.smartstore.data.entitiy.*
import com.ssafy.smartstore.data.local.dao.*

@Database(
    entities = [Notification::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun notificationDao() : NotificationDao
}