package com.ssafy.ssafybottle_manager.data.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface TokenApi {
    @POST("token")
    suspend fun postToken(@Body tokenRequest: Map<String, String>) : Response<Boolean>

}