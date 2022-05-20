package com.ssafy.smartstore.utils

import android.app.PendingIntent
import android.content.Context
import androidx.core.app.NotificationCompat
import com.ssafy.smartstore.R

fun Context.getNotificationBuilder(
    channelId: String,
    title: String,
    content: String,
    pIntent: PendingIntent
): NotificationCompat.Builder =
    NotificationCompat.Builder(this, channelId)
        .setSmallIcon(R.drawable.ic_logo_color)
        .setContentTitle(title)
        .setContentText(content)
        .setAutoCancel(true)
        .setContentIntent(pIntent)
