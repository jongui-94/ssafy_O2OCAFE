package com.ssafy.ssafybottle_manager.data.remote

import com.ssafy.ssafybottle_manager.data.dto.order.OrderIdDetailDto
import com.ssafy.ssafybottle_manager.data.dto.order.OrderListDto
import com.ssafy.ssafybottle_manager.data.dto.order.OrderRequestDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface OrderApi {
    @POST("order")
    suspend fun postOrder(@Body order: OrderRequestDto) : Response<Unit>

    @GET("order/orderList")
    suspend fun getOrderList() : Response<List<OrderListDto>>

    @GET("order/{orderId}")
    suspend fun getOrder(@Path("orderId") orderId: Int) : Response<List<OrderIdDetailDto>>

    @GET("order/completed/{orderId}")
    suspend fun completeOrder(@Path("orderId") orderId: Int) : Response<Int>

}