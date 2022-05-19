package com.ssafy.smartstore.data.dto.order

import java.sql.Timestamp

data class OrderByUserDto(
    val completed: String,
    val img: String,
    val name: String,
    val o_id: Int,
    val order_time: String,
    val p_id: Int,
    val price: Int,
    val quantity: Int,
    val type: String,
    val user_id: String
)
