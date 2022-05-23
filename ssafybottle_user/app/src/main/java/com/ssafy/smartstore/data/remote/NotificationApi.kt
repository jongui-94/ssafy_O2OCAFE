package com.ssafy.smartstore.data.remote

import com.ssafy.smartstore.data.dto.notification.NotificationDto
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface NotificationApi {
    @GET("notification/{userId}")
    suspend fun getNotifications(@Path("userId")userId: String) : Response<List<NotificationDto>>

    @DELETE("notification/{notificationId}")
    suspend fun deleteNotification(@Path("notificationId")notificationId: Int) : Response<Int>

    @DELETE("notification/all/{userId}")
    suspend fun deleteAllNotification(@Path("userId")userId: String) : Response<Int>
}