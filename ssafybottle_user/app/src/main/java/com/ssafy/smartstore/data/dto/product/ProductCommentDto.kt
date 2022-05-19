package com.ssafy.smartstore.data.dto.product

import java.io.Serializable

data class ProductCommentDto(
    val comment: String,
    val commentId: Int,
    val userName: String,
    val user_id: String,
    val rating: Int,
) : Serializable
