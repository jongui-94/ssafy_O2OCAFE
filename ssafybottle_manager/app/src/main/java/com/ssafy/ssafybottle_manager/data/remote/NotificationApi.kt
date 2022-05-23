package com.ssafy.ssafybottle_manager.data.remote

import com.ssafy.ssafybottle_manager.data.dto.notification.NotificationDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NotificationApi {
    @GET("notification/{userId}")
    suspend fun getNotifications(@Path("userId")userId: String) : Response<List<NotificationDto>>
}