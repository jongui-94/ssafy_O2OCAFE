package com.ssafy.ssafybottle_manager.data.repository

import android.content.Context
import com.ssafy.ssafybottle_manager.data.dto.card.CardDto
import com.ssafy.ssafybottle_manager.data.dto.order.OrderRequestDto
import com.ssafy.ssafybottle_manager.data.remote.CardApi
import com.ssafy.ssafybottle_manager.data.remote.OrderApi
import com.ssafy.ssafybottle_manager.data.remote.ProductApi
import com.ssafy.ssafybottle_manager.data.remote.UserApi
import com.ssafy.ssafybottle_manager.utils.retrofit.RetrofitBuilder

class Repository private constructor(context: Context) {

    /**
     API
     */
    private val productApi = RetrofitBuilder.retrofit.create(ProductApi::class.java)
    private val cardApi = RetrofitBuilder.retrofit.create(CardApi::class.java)
    private val userApi = RetrofitBuilder.retrofit.create(UserApi::class.java)
    private val orderApi = RetrofitBuilder.retrofit.create(OrderApi::class.java)


    /**
     Product
     */
    suspend fun getProducts() = productApi.getProducts()
    suspend fun getBeverage() = productApi.getBeverage()
    suspend fun getDessert() = productApi.getDessert()

    /**
     Card
     */
    suspend fun getCardHistory(userId : String) = cardApi.getCardHistory(userId)
    suspend fun postcard(card: CardDto) = cardApi.postcard(card)

    /**
     User
     */
    suspend fun checkCash(userId: String) = userApi.checkCash(userId)

    /**
     Order
     */
    suspend fun postOrder(order: OrderRequestDto) = orderApi.postOrder(order)


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