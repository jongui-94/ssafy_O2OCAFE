package com.ssafy.smartstore.data.entitiy

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.ssafy.smartstore.utils.COMMENT

@Entity(tableName = COMMENT)
data class Comment(
    @ColumnInfo(name = "user_id")
    var userId: String,

    @ColumnInfo(name = "product_id")
    var productId: Int,

    var rating: Float = 1f,

    var comment: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    @Ignore
    var isClicked = false
}