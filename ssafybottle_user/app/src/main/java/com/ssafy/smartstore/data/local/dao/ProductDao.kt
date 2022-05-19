package com.ssafy.smartstore.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.ssafy.smartstore.data.entitiy.Product

@Dao
interface ProductDao {

    @Query("SELECT * FROM t_product")
    fun getProducts() : LiveData<List<Product>>

    @Query("SELECT * FROM t_product WHERE type='coffee'")
    fun getBeverages() : LiveData<List<Product>>

    @Query("SELECT * FROM t_product WHERE type='dessert'")
    fun getDesserts() : LiveData<List<Product>>

    @Query("SELECT * FROM t_product WHERE id = (:productId)")
    suspend fun getProduct(productId: Int) : Product
}