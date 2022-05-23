package com.ssafy.ssafybottle_manager.data.remote

import com.ssafy.ssafybottle_manager.data.dto.user.UserLoginDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApi {
    @GET("user/checkCash")
    suspend fun checkCash(@Query("id")userId : String) : Response<Int>

    @POST("user/loginAdmin")
    suspend fun loginAdmin(@Body user: UserLoginDto) : Response<UserLoginDto>
}