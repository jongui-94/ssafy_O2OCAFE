package com.ssafy.smartstore.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ssafy.smartstore.data.entitiy.*
import com.ssafy.smartstore.data.local.dao.*

@Database(
    entities = [Product::class, User::class, Comment::class, Order::class, OrderDetail::class, Stamp::class, Notification::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun userDao(): UserDao
    abstract fun commentDao(): CommentDao
    abstract fun orderDao(): OrderDao
    abstract fun orderDetailDao(): OrderDetailDao
    abstract fun stampDao(): StampDao
    abstract fun notificationDao() : NotificationDao
}