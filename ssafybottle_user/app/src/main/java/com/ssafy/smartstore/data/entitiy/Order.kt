package com.ssafy.smartstore.data.entitiy

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ssafy.smartstore.utils.ORDER

@Entity(tableName = ORDER)
data class Order(
    @ColumnInfo(name = "user_id")
    var userId: String,

    @ColumnInfo(name = "order_table")
    var orderTable: String,

    @ColumnInfo(name = "order_time", defaultValue = "CURRENT_TIMESTAMP")
    var orderTime: Long? = System.currentTimeMillis(),

    @ColumnInfo(defaultValue = "N")
    var completed: String = "N"
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "o_id")
    var oId: Int = 0
}