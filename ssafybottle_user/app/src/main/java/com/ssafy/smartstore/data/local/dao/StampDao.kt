package com.ssafy.smartstore.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ssafy.smartstore.data.entitiy.Stamp

@Dao
interface StampDao {
    @Query("SELECT sum(quantity) FROM t_stamp WHERE user_id = (:userId)")
    suspend fun getUserStamps(userId : String) : Int?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStamp(stamp : Stamp) : Long

    @Query("INSERT INTO t_stamp (user_id, order_id, quantity) VALUES ((:userId), (:orderId), (SELECT sum(quantity) FROM t_order_detail WHERE order_id = (:orderId)))")
    suspend fun insertStamp(userId: String, orderId: Int) : Long
}