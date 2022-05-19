package com.ssafy.smartstore.utils

import android.app.PendingIntent
import android.content.Context
import androidx.core.app.NotificationCompat

fun Context.getNotificationBuilder(
    channelId: String,
    title: String,
    content: String,
    pIntent: PendingIntent
): NotificationCompat.Builder =
    NotificationCompat.Builder(this, channelId)
        .setSmallIcon(android.R.drawable.ic_dialog_info)
        .setContentTitle(title)
        .setContentText(content)
        .setAutoCancel(true)
        .setContentIntent(pIntent)
