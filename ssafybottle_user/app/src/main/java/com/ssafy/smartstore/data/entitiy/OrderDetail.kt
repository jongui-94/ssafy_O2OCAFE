package com.ssafy.smartstore.data.entitiy

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ssafy.smartstore.data.dto.order.OrderDetailDto
import com.ssafy.smartstore.utils.ORDER_DETAIL

@Entity(tableName = ORDER_DETAIL)
data class OrderDetail(
    @ColumnInfo(name = "order_id")
    var orderId: Int,

    @ColumnInfo(name = "product_id")
    var productId: Int,

    var quantity: Int = 1,
) {
    @ColumnInfo(name = "d_id")
    @PrimaryKey(autoGenerate = true)
    var dId: Int = 0

    fun toOrderDetailDto() : OrderDetailDto =
        OrderDetailDto(
            id = 0,
            orderId = orderId,
            productId = productId,
            quantity = quantity
        )
}