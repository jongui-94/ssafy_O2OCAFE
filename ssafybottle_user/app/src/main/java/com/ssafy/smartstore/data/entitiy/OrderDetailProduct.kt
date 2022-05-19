package com.ssafy.smartstore.data.entitiy

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.ssafy.smartstore.utils.ORDER_DETAIL_PRODUCT

@Entity(tableName = ORDER_DETAIL_PRODUCT)
data class OrderDetailProduct(
    @ColumnInfo(name = "d_id")
    var orderDetailId : Int,

    @ColumnInfo(name = "quantity")
    var quantity : Int,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "img")
    var img: String,

    @ColumnInfo(name = "price")
    var price: Int,
)