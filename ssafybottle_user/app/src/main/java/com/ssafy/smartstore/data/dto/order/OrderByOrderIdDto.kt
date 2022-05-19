package com.ssafy.smartstore.data.dto.order

data class OrderByOrderIdDto(
    val completed: String,
    val img: String,
    val name: String,
    val o_id: Int,
    val order_table: String,
    val order_time: String,
    val p_id: Int,
    val quantity: Int,
    val stamp: Int,
    val totalprice: Int,
    val type: String,
    val unitprice: Int
)