package com.ssafy.smartstore.data.entitiy

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.ssafy.smartstore.utils.SHOPPING_ITEM

@Entity(tableName = SHOPPING_ITEM)
data class ShoppingItem(
    @ColumnInfo(name = "d_id")
    var orderDetailId : Int,

    @ColumnInfo(name = "name")
    var Name : String,

    @ColumnInfo(name = "img")
    var Img : String,

    @ColumnInfo(name = "quantity")
    var quantity : Int,

    @ColumnInfo(name = "price")
    var price : Int,

    @ColumnInfo(name = "sum")
    var totalPrice : Int
)