package com.ssafy.smartstore.data.dto.user

data class UserInfoResponseDto(
    val grade: GradeDto,
    val order: List<UserInfoOrderDto>,
    val user: UserDto
)