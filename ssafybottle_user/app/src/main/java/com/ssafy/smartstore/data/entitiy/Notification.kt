package com.ssafy.smartstore.data.entitiy

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ssafy.smartstore.utils.NOTIFICATION

@Entity(tableName = NOTIFICATION)
data class Notification(
    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "content")
    var content: String,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}