package com.ssafy.smartstore.data.remote

import com.ssafy.smartstore.data.dto.comment.CommentDto
import retrofit2.Response
import retrofit2.http.*

interface CommentApi {
    @POST("comment")
    suspend fun insertComment(@Body comment: CommentDto) : Response<Unit>

    @PUT("comment")
    suspend fun updateComment(@Body comment: CommentDto) : Response<Unit>

    @DELETE("comment/{id}")
    suspend fun deleteComment(@Path("id") id: Int) : Response<Unit>
}