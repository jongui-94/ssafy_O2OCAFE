package com.ssafy.ssafybottle_manager.data.remote

import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Path

interface CommentApi {

    @DELETE("comment/{id}")
    suspend fun deleteComment(@Path("id") id: Int): Response<Unit>
}