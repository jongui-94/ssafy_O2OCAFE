package com.ssafy.ssafybottle_manager.core.service

import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.ssafy.ssafybottle_manager.application.MainActivity
import com.ssafy.ssafybottle_manager.data.repository.Repository
import com.ssafy.ssafybottle_manager.utils.ADMIN_ID
import com.ssafy.ssafybottle_manager.utils.getNotificationBuilder
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FCMService : FirebaseMessagingService() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        Log.d("FCMService_싸피", "onNewToken: 토큰 갱신: $token")
        CoroutineScope(Dispatchers.IO).launch(exceptionHandler) {
            Repository.get().postToken(mapOf("userId" to ADMIN_ID, "token" to token))
        }
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
        }
    }
}