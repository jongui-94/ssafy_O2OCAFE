package com.ssafy.smartstore.data.repository

import android.content.Context
import androidx.room.Room
import androidx.room.withTransaction
import com.ssafy.smartstore.data.dto.card.CardDto
import com.ssafy.smartstore.data.dto.comment.CommentDto
import com.ssafy.smartstore.data.dto.order.OrderByUserDto
import com.ssafy.smartstore.data.dto.order.OrderRequestDto
import com.ssafy.smartstore.data.dto.product.ProductDto
import com.ssafy.smartstore.data.dto.user.UserDto
import com.ssafy.smartstore.data.entitiy.*
import com.ssafy.smartstore.data.local.AppDatabase
import com.ssafy.smartstore.data.remote.*
import com.ssafy.smartstore.utils.DATABASE
import com.ssafy.smartstore.utils.retrofit.RetrofitBuilder
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

class Repository private constructor(context: Context) {
    private val database: AppDatabase = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        DATABASE
    ).build()

    /**
    Dao
     */
    private val notificationDao = database.notificationDao()

    /**
    Api
     */
    private val productApi = RetrofitBuilder.retrofit.create(ProductApi::class.java)
    private val orderApi = RetrofitBuilder.retrofit.create(OrderApi::class.java)
    private val userApi = RetrofitBuilder.retrofit.create(UserApi::class.java)
    private val commentApi = RetrofitBuilder.retrofit.create(CommentApi::class.java)
    private val cardApi = RetrofitBuilder.retrofit.create(CardApi::class.java)

    /**
    Product
     */
    // Api
    suspend fun getProducts() = productApi.getProducts()
    suspend fun getBeverage() = productApi.getBeverage()
    suspend fun getDessert() = productApi.getDessert()
    suspend fun getProductDetail(productId: Int) = productApi.getProductDetail(productId)
    suspend fun getProductRecommend() = productApi.getProductRecommend()
    suspend fun getProductTop10() = productApi.getProductTop10()

    /**
    User
     */
    // Api
    suspend fun checkUserId(userId: String) = userApi.checkUserId(userId)
    suspend fun insertUser(user: UserDto) = userApi.insertUser(user)
    suspend fun login(user: UserDto) = userApi.login(user)
    suspend fun getUserInfo(userId: String) = userApi.getUserInfo(userId)
    suspend fun checkCash(userId: String) = userApi.checkCash(userId)

    /**
    Comment
     */
    //Api
    suspend fun insertComment(comment: CommentDto) = commentApi.insertComment(comment)
    suspend fun updateComment(comment: CommentDto) = commentApi.updateComment(comment)
    suspend fun deleteComment(id: Int) = commentApi.deleteComment((id))

    /**
    Order
     */
    // Api
    suspend fun getOrderByUser(userId: String) = orderApi.getOrderByUser(userId)
    suspend fun getOrderByOrderId(orderId: Int) = orderApi.getOrderByOrderId(orderId)
    suspend fun postOrder(order: OrderRequestDto) = orderApi.postOrder(order)

    /**
    Notification
     */
    fun getNotifications() = notificationDao.getNotifications()
    suspend fun insertNotification(notification: Notification) =
        database.withTransaction { notificationDao.insertNotification(notification) }

    suspend fun deleteNotification(notification: Notification) =
        database.withTransaction { notificationDao.deleteNotification(notification) }

    /**
    Card
     */
    suspend fun getCardHistory(userId : String) = cardApi.getCardHistory(userId)
    suspend fun postcard(card: CardDto) = cardApi.postcard(card)

    /**
    Singleton
     */
    companion object {
        private var instance: Repository? = null

        fun initialize(context: Context) {
            if (instance == null) {
                instance = Repository(context)
            }
        }

        fun get(): Repository {
            return instance ?: throw IllegalStateException("GalleryRepository must be initialized")
        }
    }
}