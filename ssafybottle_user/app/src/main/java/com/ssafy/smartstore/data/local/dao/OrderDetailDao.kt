package com.ssafy.smartstore.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ssafy.smartstore.data.entitiy.RecentOrder
import com.ssafy.smartstore.data.entitiy.ShoppingItem
import com.ssafy.smartstore.data.entitiy.OrderDetail
import com.ssafy.smartstore.data.entitiy.OrderDetailProduct

@Dao
interface OrderDetailDao {

    @Query("select t_order.o_id, t_product.img, t_product.name, sum(t_order_detail.quantity) as 'totalQuantity', sum(t_order_detail.quantity * t_product.price) as 'totalPrice', t_order.order_time\n" +
            "from t_order, t_product, t_order_detail, t_user\n" +
            "where t_user.id = (:userId) and t_order.user_id = t_user.id and t_order.o_id = t_order_detail.order_id and t_order_detail.product_id = t_product.id\n" +
            "group by t_order.o_id;")
    suspend fun getRecentOrders(userId : String) : List<RecentOrder>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrderDetail(orderDetail: OrderDetail) : Long

    @Query("select t_order_detail.d_id, t_product.name, t_product.img, t_order_detail.quantity, t_product.price, sum(t_product.price * t_order_detail.quantity) as 'sum' from t_order_detail, t_product where t_order_detail.product_id = t_product.id and t_order_detail.order_id = (:orderId) group by t_order_detail.d_id")
    suspend fun getShoppingList(orderId : Int) : List<ShoppingItem>

    @Query("select t_order_detail.d_id, t_order_detail.quantity, t_product.name, t_product.img, t_product.price\n" +
            "from t_order_detail, t_product\n" +
            "where t_order_detail.order_id = (:orderId) and t_order_detail.product_id = t_product.id;")
    suspend fun getOrderDetailProducts(orderId : Int) : List<OrderDetailProduct>

    @Query("select * from t_order_detail where order_id = (:orderId)")
    suspend fun getOrderDetails(orderId: Int) : List<OrderDetail>

    @Query("SELECT sum(quantity) FROM t_order_detail WHERE order_id = (:orderId)")
    suspend fun getOrderQuantity(orderId: Int) : Int
}