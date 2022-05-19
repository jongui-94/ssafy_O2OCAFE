package com.ssafy.smartstore.ui.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.smartstore.application.SmartStoreApplication.Companion.tableName
import com.ssafy.smartstore.base.BaseViewModel
import com.ssafy.smartstore.data.dto.order.OrderDetailDto
import com.ssafy.smartstore.data.dto.order.OrderRequestDto
import com.ssafy.smartstore.data.dto.product.ProductDto
import com.ssafy.smartstore.data.dto.stamp.StampDto
import com.ssafy.smartstore.data.entitiy.Order
import com.ssafy.smartstore.data.entitiy.OrderDetail
import com.ssafy.smartstore.data.entitiy.ShoppingItem
import com.ssafy.smartstore.data.repository.Repository
import kotlinx.coroutines.launch

class ShoppingListViewModel : BaseViewModel() {
    private val repository = Repository.get()

    private val _shoppingList = MutableLiveData<List<ShoppingItem>>()
    val shoppingList: LiveData<List<ShoppingItem>> get() = _shoppingList

    private val _isComplete = MutableLiveData<Boolean>()
    val isComplete: LiveData<Boolean> get() = _isComplete

    fun getShoppingList(orderId: Int) {
        viewModelScope.launch(exceptionHandler) {
            // orderDetail 리스트 가져오기
            var orderDetails: List<OrderDetail> = emptyList()
            var products : List<ProductDto> = emptyList()
            launch(exceptionHandler) {
                repository.getOrderDetails(orderId).let {
                    orderDetails = it
                }
                repository.getProducts().let {
                    if(it.isSuccessful) {
                        it.body()?.let {
                            products = it
                        }
                    }
                }
            }.join()

            // OrderDetail 리스트 체크
            if (orderDetails.isEmpty() || products.isEmpty()) {
                return@launch
            }

            val shoppingList = mutableListOf<ShoppingItem>()
            products.forEach { product ->
                orderDetails.forEach { orderDetail ->
                    if(product.id == orderDetail.productId) {
                        shoppingList.add(
                            ShoppingItem(
                                orderDetailId = orderDetail.dId,
                                Name = product.name,
                                Img = product.img,
                                quantity = orderDetail.quantity,
                                price = product.price,
                                totalPrice = product.price * orderDetail.quantity
                            )
                        )
                    }
                }
            }

            if(shoppingList.isNotEmpty()) {
                _shoppingList.postValue(shoppingList)
            }
        }
    }

    fun completeOrder(orderId: Int) {
        viewModelScope.launch(exceptionHandler) {
            // orderDetail 리스트 가져오기
            var orderDetail: List<OrderDetail> = emptyList()
            launch {
                repository.getOrderDetails(orderId).let {
                    orderDetail = it
                }
            }.join()

            // OrderDetail 리스트 체크
            if (orderDetail.isEmpty()) {
                _isComplete.postValue(false)
                return@launch
            }

            // Order 가져오기
            var order: Order? = null
            launch {
                repository.getOrder(orderId).let {
                    order = it
                }
            }.join()

            // Order 체크
            if (order == null) {
                _isComplete.postValue(false)
                return@launch
            }

            var stamps = 0
            val details: List<OrderDetailDto> = orderDetail.map {
                stamps += it.quantity
                it.toOrderDetailDto()
            }

            val stamp = StampDto(
                id = 0,
                orderId = orderId,
                quantity = stamps,
                userId = order!!.userId
            )

            val orderRequestDto = OrderRequestDto(
                completed = 'Y',
                details = details,
                id = 0,
                orderTable = tableName,
                //orderTime = ,
                stamp = stamp,
                userId = order!!.userId
            )


            repository.postOrder(orderRequestDto).let {
                if(it.isSuccessful) {
                    tableName = ""
                    _isComplete.postValue(true)
                } else {
                    _isComplete.postValue(false)
                }
            }
        }
    }
}