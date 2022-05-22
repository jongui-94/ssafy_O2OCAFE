package com.ssafy.ssafybottle_manager.data.repository

import android.content.Context
import com.ssafy.ssafybottle_manager.data.remote.ProductApi
import com.ssafy.ssafybottle_manager.utils.retrofit.RetrofitBuilder

class Repository private constructor(context: Context) {

    /**
     API
     */
    private val productApi = RetrofitBuilder.retrofit.create(ProductApi::class.java)


    /**
     Product
     */
    suspend fun getProducts() = productApi.getProducts()
    suspend fun getBeverage() = productApi.getBeverage()
    suspend fun getDessert() = productApi.getDessert()



    companion object {
        private var instance: Repository? = null

        fun initialize(context: Context) {
            if (instance == null) {
                instance = Repository(context)
            }
        }

        fun get(): Repository {
            return instance ?: throw IllegalStateException("Repository must be initialized")
        }
    }
}