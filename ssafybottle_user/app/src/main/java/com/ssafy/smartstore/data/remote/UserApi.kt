package com.ssafy.smartstore.data.remote

import com.ssafy.smartstore.data.dto.user.UserDto
import com.ssafy.smartstore.data.dto.user.UserInfoResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApi {
    @GET("user/isUsed")
    suspend fun checkUserId(@Query("id")userId : String) : Response<Boolean>

    @POST("user")
    suspend fun insertUser(@Body user: UserDto) : Response<Unit>

    @POST("user/login")
    suspend fun login(@Body user: UserDto) : Response<UserDto>

    @POST("user/info")
    suspend fun getUserInfo(@Query("id") id: String) : Response<UserInfoResponseDto>
}