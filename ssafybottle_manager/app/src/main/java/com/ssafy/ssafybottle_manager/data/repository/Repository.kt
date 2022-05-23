package com.ssafy.ssafybottle_manager.data.repository

import android.content.Context
import com.ssafy.ssafybottle_manager.data.dto.card.CardDto
import com.ssafy.ssafybottle_manager.data.dto.order.OrderRequestDto
import com.ssafy.ssafybottle_manager.data.dto.product.ProductSalesDto
import com.ssafy.ssafybottle_manager.data.dto.user.UserLoginDto
import com.ssafy.ssafybottle_manager.data.remote.*
import com.ssafy.ssafybottle_manager.utils.retrofit.RetrofitBuilder
import retrofit2.Response
import retrofit2.http.GET

class Repository private constructor(context: Context) {

    /**
    API
     */
    private val productApi = RetrofitBuilder.retrofit.create(ProductApi::class.java)
    private val cardApi = RetrofitBuilder.retrofit.create(CardApi::class.java)
    private val userApi = RetrofitBuilder.retrofit.create(UserApi::class.java)
    private val orderApi = RetrofitBuilder.retrofit.create(OrderApi::class.java)
    private val notificationApi = RetrofitBuilder.retrofit.create(NotificationApi::class.java)
    private val tokenApi = RetrofitBuilder.retrofit.create(TokenApi::class.java)


    /**
    Product
     */
    suspend fun getProducts() = productApi.getProducts()
    suspend fun getBeverage() = productApi.getBeverage()
    suspend fun getDessert() = productApi.getDessert()
    suspend fun getTotalSales() = productApi.getTotalSales()
    suspend fun getProductSales() = productApi.getProductSales()
    suspend fun getProductDetail(productId: Int) = productApi.getProductDetail(productId)

    /**
    Card
     */
    suspend fun getCardHistory(userId: String) = cardApi.getCardHistory(userId)
    suspend fun postcard(card: CardDto) = cardApi.postcard(card)

    /**
    User
     */
    suspend fun checkCash(userId: String) = userApi.checkCash(userId)
    suspend fun loginAdmin(user: UserLoginDto) = userApi.loginAdmin(user)

    /**
    Order
     */
    suspend fun postOrder(order: OrderRequestDto) = orderApi.postOrder(order)
    suspend fun getOrderList() = orderApi.getOrderList()
    suspend fun getOrder(orderId : Int) = orderApi.getOrder(orderId)
    suspend fun completeOrder(orderId: Int) = orderApi.completeOrder(orderId)

    /**
    Notification
     */
    suspend fun getNotifications(userId: String) = notificationApi.getNotifications(userId)
    suspend fun deleteNotification(notificationId: Int) = notificationApi.deleteNotification(notificationId)
    suspend fun deleteAllNotification(userId: String) = notificationApi.deleteAllNotification(userId)

    /**
    Token
     */
    suspend fun postToken(tokenRequest: Map<String, String>) = tokenApi.postToken(tokenRequest)


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