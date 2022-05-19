package com.ssafy.smartstore.data.entitiy

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ssafy.smartstore.utils.PRODUCT

@Entity(tableName = PRODUCT)
data class Product(
    var name: String,
    var type: String,
    var price : Int,
    var img : String
) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}