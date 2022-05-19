package com.ssafy.smartstore.data.entitiy

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.ssafy.smartstore.utils.RECENT_ORDER

@Entity(tableName = RECENT_ORDER)
data class RecentOrder(
    @ColumnInfo(name = "o_id")
    var orderId : Int,

    @ColumnInfo(name = "img")
    var img : String,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "totalQuantity")
    var totalQuantity: Int,

    @ColumnInfo(name = "totalPrice")
    var totalPrice: Int,

    @ColumnInfo(name = "order_time")
    var orderTime: Long
)