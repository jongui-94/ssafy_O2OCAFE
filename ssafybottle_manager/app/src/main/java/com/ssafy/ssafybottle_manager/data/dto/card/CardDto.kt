package com.ssafy.ssafybottle_manager.data.dto.card

data class CardDto(
    val id: Int,
    val userId: String,
    val orderId: Int,
    val payment: Int,
    val content: String,
    val payTime: String
    )