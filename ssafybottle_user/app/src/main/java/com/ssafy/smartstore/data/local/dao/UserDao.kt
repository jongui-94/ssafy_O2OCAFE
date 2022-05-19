package com.ssafy.smartstore.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ssafy.smartstore.data.entitiy.User

@Dao
interface UserDao {
    @Query("SELECT * FROM t_user WHERE id = (:id)")
    suspend fun getUser(id: String) : User

    @Query("SELECT count(*) FROM t_user WHERE id = (:id) AND pass = (:pass)")
    suspend fun loginUser(id: String, pass: String) : Int

    @Query("SELECT count(*) FROM t_user WHERE id = (:id)")
    suspend fun compareUserId(id: String) : Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User) : Long
}