package com.ssafy.smartstore.data.dto.comment

import com.ssafy.smartstore.data.dto.stamp.StampDto

data class CommentDto(
    val comment: String,
    val id: Int,
    val productId: Int,
    val rating: Int,
    val userId: String
)
