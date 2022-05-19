package com.ssafy.smartstore.data.entitiy

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "t_user")
data class User(
    @PrimaryKey var id: String,

    var name: String,

    var pass: String,

    var stamps: Int = 0
)