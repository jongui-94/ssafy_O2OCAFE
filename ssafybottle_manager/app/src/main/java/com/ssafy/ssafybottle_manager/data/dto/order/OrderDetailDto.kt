package com.ssafy.ssafybottle_manager.data.dto.order

data class OrderDetailDto(
    val productId: Int,
    var quantity: Int,
    val img: String,
    val name: String,
    val price: Int
)