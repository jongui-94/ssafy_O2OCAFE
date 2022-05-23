package com.ssafy.ssafybottle_manager.data.remote

import com.ssafy.ssafybottle_manager.data.dto.order.OrderRequestDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface OrderApi {
    @POST("order")
    suspend fun postOrder(@Body order: OrderRequestDto) : Response<Unit>
}