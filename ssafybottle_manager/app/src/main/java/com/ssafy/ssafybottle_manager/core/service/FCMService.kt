package com.ssafy.ssafybottle_manager.core.service

import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.ssafy.ssafybottle_manager.application.MainActivity
import com.ssafy.ssafybottle_manager.utils.getNotificationBuilder

class FCMService : FirebaseMessagingService() {

    //private lateinit var repository: Repository

    override fun onCreate() {
        super.onCreate()

        //repository = Repository.get()
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        Log.d("FCMService_싸피", "onNewToken: 토큰 갱신: $token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        Log.d("FCMService_싸피", message.toString())

        message.notification?.let {
            val intent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }

            val pIntent = PendingIntent.getActivity(this, 0, intent, 0)

            NotificationManagerCompat.from(this).notify(
                101,
                getNotificationBuilder(
                    "smart_store",
                    it.title ?: "",
                    it.body ?: "",
                    pIntent
                ).build()
            )
            //val userId = getUserId()
            //insertNotification(userId, it.title ?: "", it.body ?: "")
        }
    }

//    private fun insertNotification(userId: String, title: String, content: String) {
//        CoroutineScope(Dispatchers.Main).launch {
//            repository.insertNotification(
//                Notification(
//                    userId, title, content
//                )
//            )
//        }
//    }
}