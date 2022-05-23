package com.ssafy.ssafybottle_manager.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {
    @GET("user/checkCash")
    suspend fun checkCash(@Query("id")userId : String) : Response<Int>
}