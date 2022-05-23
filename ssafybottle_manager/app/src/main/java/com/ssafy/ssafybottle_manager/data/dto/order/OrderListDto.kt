package com.ssafy.ssafybottle_manager.data.dto.order

import com.google.gson.annotations.SerializedName
import java.util.*

data class OrderListDto(
    val img: String,

    @SerializedName("user_id")
    val userId: String,

    @SerializedName("o_id")
    val orderId: Int,

    @SerializedName("user_name")
    val userName: String,

    @SerializedName("product_name")
    val productName: String,

    @SerializedName("total_quantity")
    val totalQuantity: Int,

    @SerializedName("total_price")
    val totalPrice: Int,

    @SerializedName("order_time")
    val orderTime: Date,

    val completed: String,

    var isSelected : Boolean = false
)