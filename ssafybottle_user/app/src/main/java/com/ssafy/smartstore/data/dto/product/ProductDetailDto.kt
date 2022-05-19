package com.ssafy.smartstore.data.dto.product

data class ProductDetailDto(
    val img: String,
    val price: Int,
    val name: String,
    val type: String,
    val commentCnt: Int,
    val avg: Float = 0f,
    val rating: Int = 0,
    val sells: Int = 0
)