package com.ssafy.ssafybottle_manager.data.dto.order

import java.util.*

data class OrderIdDetailDto(
    val completed: String,
    val img: String,
    val name: String,
    val o_id: Int,
    val order_table: String,
    val order_time: Date,
    val p_id: Int,
    val quantity: Int,
    val stamp: Int,
    val totalprice: Int,
    val type: String,
    val unitprice: Int
)