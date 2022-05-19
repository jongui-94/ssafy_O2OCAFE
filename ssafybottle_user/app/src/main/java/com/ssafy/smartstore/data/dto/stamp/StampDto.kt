package com.ssafy.smartstore.data.dto.stamp

data class StampDto(
    val id: Int,
    val orderId: Int,
    val quantity: Int,
    val userId: String
)