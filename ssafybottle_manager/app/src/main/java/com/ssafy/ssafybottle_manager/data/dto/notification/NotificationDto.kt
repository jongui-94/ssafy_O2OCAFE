package com.ssafy.ssafybottle_manager.data.dto.notification

data class NotificationDto(
    val id: Int,
    var userId: String,
    val title: String,
    val content: String,
    val time: String
)