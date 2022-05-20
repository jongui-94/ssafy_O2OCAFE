package com.ssafy.smartstore.data.dto.order

data class OrderDetailDto(
    val productId: Int,
    val quantity: Int,
    val img: String,
    val name: String,
    val price: Int
)