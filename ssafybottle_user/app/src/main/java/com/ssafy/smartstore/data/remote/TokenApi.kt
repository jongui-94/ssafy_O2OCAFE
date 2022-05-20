package com.ssafy.smartstore.data.remote

import com.ssafy.smartstore.data.dto.user.UserDto
import com.ssafy.smartstore.data.dto.user.UserInfoResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface TokenApi {
    @POST("token")
    suspend fun postToken(@Body tokenRequest: Map<String, String>) : Response<Boolean>

}