package com.ssafy.smartstore.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ssafy.smartstore.data.entitiy.Order

@Dao
interface OrderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: Order) : Long

    @Query("SELECT o_id FROM t_order ORDER BY o_id DESC LIMIT 1")
    suspend fun getLastOrderId() : Int

    @Query("UPDATE t_order SET completed = 'Y' WHERE o_id = (:orderId)")
    suspend fun completeOrder(orderId : Int) : Int

    @Query("SELECT * FROM t_order WHERE o_id = (:orderId)")
    suspend fun getOrder(orderId: Int) : Order
}