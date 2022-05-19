package com.ssafy.smartstore.data.local.dao

import androidx.room.*
import com.ssafy.smartstore.data.entitiy.Comment

@Dao
interface CommentDao {
    @Query("SELECT * FROM t_comment WHERE product_id = (:productId)")
    suspend fun getComments(productId : Int) : List<Comment>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComment(comment: Comment) : Long

    @Delete
    suspend fun deleteComment(comment: Comment) : Int

    @Update
    suspend fun updateComment(comment: Comment) : Int
}