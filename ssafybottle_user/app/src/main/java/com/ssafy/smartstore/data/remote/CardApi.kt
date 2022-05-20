package com.ssafy.smartstore.data.remote

import com.ssafy.smartstore.data.dto.card.CardDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CardApi {
    @GET("card/{userId}")
    suspend fun getCardHistory(@Path("userId") userId: String): Response<List<CardDto>>

    @POST("card")
    suspend fun postcard(@Body card: CardDto): Response<Boolean>
}