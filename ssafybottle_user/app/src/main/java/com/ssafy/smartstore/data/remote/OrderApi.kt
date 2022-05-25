package com.ssafy.smartstore.data.remote

import com.ssafy.smartstore.data.dto.order.OrderByOrderIdDto
import com.ssafy.smartstore.data.dto.order.OrderByUserDto
import com.ssafy.smartstore.data.dto.order.OrderRequestDto
import retrofit2.Response
import retrofit2.http.*

interface OrderApi {
    @GET("order/byUser")
    suspend fun getOrderByUser(@Query("id")userId : String) : Response<List<OrderByUserDto>>

    @GET("order/{orderId}")
    suspend fun getOrderByOrderId(@Path("orderId") orderId: Int) : Response<List<OrderByOrderIdDto>>

    @POST("order")
    suspend fun postOrder(@Body order: OrderRequestDto) : Response<Unit>

    @GET("order/isOrdered/{userId}")
    suspend fun getOrderedProductIds(@Path("userId") userId: String) : Response<List<Int>>
}