package com.ssafy.smartstore.data.dto.order

import com.ssafy.smartstore.data.dto.stamp.StampDto
import java.sql.Date

data class OrderRequestDto(
    val completed: Char,
    val details: List<OrderDetailDto>,
    val id: Int,
    val orderTable: String,
    //val orderTime: Date,
    val stamp: StampDto,
    val userId: String
)