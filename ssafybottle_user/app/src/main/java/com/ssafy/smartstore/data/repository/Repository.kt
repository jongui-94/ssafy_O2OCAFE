package com.ssafy.smartstore.data.repository

import android.content.Context
import androidx.room.Room
import androidx.room.withTransaction
import com.ssafy.smartstore.data.dto.comment.CommentDto
import com.ssafy.smartstore.data.dto.order.OrderRequestDto
import com.ssafy.smartstore.data.dto.user.UserDto
import com.ssafy.smartstore.data.entitiy.*
import com.ssafy.smartstore.data.local.AppDatabase
import com.ssafy.smartstore.data.remote.CommentApi
import com.ssafy.smartstore.data.remote.OrderApi
import com.ssafy.smartstore.data.remote.ProductApi
import com.ssafy.smartstore.data.remote.UserApi
import com.ssafy.smartstore.utils.DATABASE
import com.ssafy.smartstore.utils.retrofit.RetrofitBuilder

class Repository private constructor(context: Context) {
    private val database: AppDatabase = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        DATABASE
    ).build()

    /**
    Dao
     */
    private val stampDao = database.stampDao()
    private val commentDao = database.commentDao()
    private val orderDao = database.orderDao()
    private val orderDetailDao = database.orderDetailDao()
    private val productDao = database.productDao()
    private val userDao = database.userDao()
    private val notificationDao = database.notificationDao()

    /**
    Api
     */
    private val productApi = RetrofitBuilder.retrofit.create(ProductApi::class.java)
    private val orderApi = RetrofitBuilder.retrofit.create(OrderApi::class.java)
    private val userApi = RetrofitBuilder.retrofit.create(UserApi::class.java)
    private val commentApi = RetrofitBuilder.retrofit.create((CommentApi::class.java))

    /**
    Product
     */

    // Api
    suspend fun getProducts() = productApi.getProducts()
    suspend fun getBeverage() = productApi.getBeverage()
    suspend fun getDessert() = productApi.getDessert()
    suspend fun getProductDetail(productId: Int) = productApi.getProductDetail(productId)

    // Room
    //fun getProducts() = productDao.getProducts()
    //fun getBeverages() = productDao.getBeverages()
    //fun getDesserts() = productDao.getDesserts()
    //suspend fun getProduct(productId : Int) = productDao.getProduct(productId)

    /**
    User
     */

    // Api
    suspend fun checkUserId(userId: String) = userApi.checkUserId(userId)
    suspend fun insertUser(user: UserDto) = userApi.insertUser(user)
    suspend fun login(user: UserDto) = userApi.login(user)
    suspend fun getUserInfo(userId: String) = userApi.getUserInfo(userId)

    // Room

    //suspend fun getUser(id: String) = userDao.getUser(id)
    //suspend fun insertUser(user: User): Long = database.withTransaction { userDao.insertUser(user) }
    //suspend fun compareUserId(id: String) : Int = userDao.compareUserId(id)
    //suspend fun loginUser(id: String, pass: String) : Int = userDao.loginUser(id, pass)


    /**
    Comment
     */
//    suspend fun getComments(productId: Int) = commentDao.getComments(productId)
//    suspend fun insertComment(comment : Comment) = database.withTransaction { commentDao.insertComment(comment) }
//    suspend fun deleteComment(comment : Comment) = database.withTransaction { commentDao.deleteComment(comment) }
//    suspend fun updateComment(comment : Comment) = database.withTransaction { commentDao.updateComment(comment) }

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

    // Room
    suspend fun insertOrder(order: Order) = database.withTransaction { orderDao.insertOrder(order) }
    suspend fun getOrder(orderId : Int) = orderDao.getOrder(orderId)
    suspend fun getLastOrderId() = orderDao.getLastOrderId()
    suspend fun completeOrder(orderId: Int) = orderDao.completeOrder(orderId)

    /**
    OrderDetail
     */
    suspend fun insertOrderDetail(orderDetail: OrderDetail) =
        database.withTransaction { orderDetailDao.insertOrderDetail(orderDetail) }

    suspend fun getShoppingList(orderId: Int) = orderDetailDao.getShoppingList(orderId)
    suspend fun getOrderDetails(orderId: Int) = orderDetailDao.getOrderDetails(orderId)
    suspend fun getRecentOrders(userId: String) = orderDetailDao.getRecentOrders(userId)
    suspend fun getOrderDetailProducts(orderId: Int) =
        orderDetailDao.getOrderDetailProducts(orderId)

    suspend fun getOrderQuantity(orderId: Int) = orderDetailDao.getOrderQuantity(orderId)

    /**
    Stamp
     */
//    suspend fun getUserStamps(userId: String) = stampDao.getUserStamps(userId)
//    suspend fun insertStamp(stamp: Stamp) = database.withTransaction { stampDao.insertStamp(stamp) }
//    suspend fun insertStamp(userId: String, orderId: Int) = database.withTransaction { stampDao.insertStamp(userId, orderId) }

    /**
    Notification
     */
    fun getNotifications() = notificationDao.getNotifications()
    suspend fun insertNotification(notification: Notification) =
        database.withTransaction { notificationDao.insertNotification(notification) }

    suspend fun deleteNotification(notification: Notification) =
        database.withTransaction { notificationDao.deleteNotification(notification) }

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