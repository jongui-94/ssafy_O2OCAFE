package com.ssafy.ssafybottle_manager.data.dto.order

data class OrderRequestDto(
    val details: MutableList<OrderDetailDto>,
    val orderTable: String,
    val userId: String
)