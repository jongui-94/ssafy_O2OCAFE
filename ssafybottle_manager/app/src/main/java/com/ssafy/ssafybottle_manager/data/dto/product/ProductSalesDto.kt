package com.ssafy.ssafybottle_manager.data.dto.product

import com.google.gson.annotations.SerializedName

data class ProductSalesDto (
    val img : String,
    val name: String,
    @SerializedName("id")
    val productId : Int,
    val rating: Float = 0f,
    val sales: Int,
    val type: String
)