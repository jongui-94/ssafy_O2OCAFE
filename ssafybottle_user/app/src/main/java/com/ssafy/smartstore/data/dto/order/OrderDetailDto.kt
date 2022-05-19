package com.ssafy.smartstore.data.dto.order

data class OrderDetailDto(
    val id: Int,
    val orderId: Int,
    val productId: Int,
    val quantity: Int
)