package com.ssafy.smartstore.data.dto.order

data class OrderRequestDto(
    val details: MutableList<OrderDetailDto>,
    val orderTable: String,
    val userId: String
)