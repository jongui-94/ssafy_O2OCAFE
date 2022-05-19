package com.ssafy.smartstore.data.dto.user

import com.ssafy.smartstore.data.dto.stamp.StampDto

data class UserDto(
    val id: String,
    val name: String,
    val pass: String,
    val stampList: List<StampDto>,
    val stamps: Int
)