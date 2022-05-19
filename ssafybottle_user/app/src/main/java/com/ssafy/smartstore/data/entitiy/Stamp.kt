package com.ssafy.smartstore.data.entitiy

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ssafy.smartstore.utils.STAMP

@Entity(tableName = STAMP)
data class Stamp(
    @ColumnInfo(name = "user_id")
    var userId: String,

    @ColumnInfo(name = "order_id")
    var orderId: Int,

    var quantity: Int = 1,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}