package com.ssafy.smartstore.data.dto.user

data class UserInfoOrderDto(
    val completed: String,
    val details: Any,
    val id: Int,
    val orderTable: String,
    val orderTime: String,
    val stamp: Any,
    val userId: String
)