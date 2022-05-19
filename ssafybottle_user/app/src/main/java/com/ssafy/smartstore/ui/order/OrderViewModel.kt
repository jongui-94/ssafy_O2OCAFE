package com.ssafy.smartstore.ui.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.smartstore.application.SmartStoreApplication.Companion.isOrder
import com.ssafy.smartstore.application.SmartStoreApplication.Companion.orderId
import com.ssafy.smartstore.base.BaseViewModel
import com.ssafy.smartstore.data.dto.product.ProductCommentDto
import com.ssafy.smartstore.data.dto.product.ProductDetailDto
import com.ssafy.smartstore.data.entitiy.Order
import com.ssafy.smartstore.data.entitiy.OrderDetail
import com.ssafy.smartstore.data.repository.Repository
import kotlinx.coroutines.launch

class OrderViewModel : BaseViewModel() {
    private val repository : Repository = Repository.get()

    private val _product = MutableLiveData<ProductDetailDto>()
    val product : LiveData<ProductDetailDto> get() = _product

    private val _comments = MutableLiveData<List<ProductCommentDto>>()
    val comments : LiveData<List<ProductCommentDto>> get() = _comments

    private val _isComplete = MutableLiveData<Boolean>()
    val isComplete : LiveData<Boolean> get() = _isComplete

    private val _isDeleted = MutableLiveData<Boolean>()
    val isDeleted : LiveData<Boolean> get() = _isDeleted


    fun getProduct(productId: Int) {
        viewModelScope.launch(exceptionHandler) {
            repository.getProductDetail(productId).let { response ->
                if(response.isSuccessful) {
                    response.body()?.let {
                        val productDetail = it[0].toProductDetailDto()
                        _product.postValue(productDetail)

                        // 코멘트 있으면
                        if(productDetail.commentCnt > 0) {
                            val productComments : List<ProductCommentDto> = it.map { productDetailResponse ->
                                productDetailResponse.toProductCommentDto()
                            }
                            _comments.postValue(productComments)
                        }
                    }
                }
            }
        }
    }

    fun deleteComment(commentId: Int) {
        viewModelScope.launch(exceptionHandler) {
            repository.deleteComment(commentId).let {
                if(it.isSuccessful) {
                    _isDeleted.postValue(true)
                } else {
                    _isDeleted.postValue(false)
                }
            }
        }
    }

    fun insertFirstOrder(order: Order, orderDetail: OrderDetail) {
        viewModelScope.launch {
            var result = 0L

            // Order 생성
            launch {
                repository.insertOrder(order).let {
                    result = it
                }
            }.join()

            launch {
                if(result > 0) {
                    repository.getLastOrderId().let {
                        orderId = it
                        isOrder = true
                    }
                } else {
                    _isComplete.postValue(false)
                }
            }.join()

            if(isOrder) {
                orderDetail.orderId = orderId
                repository.insertOrderDetail(orderDetail).let {
                    if (it > 0) {
                        _isComplete.postValue(true)
                    }
                }
            }
        }
    }

    fun insertOrder(orderDetail: OrderDetail) {
        viewModelScope.launch {
            orderDetail.orderId = orderId
            repository.insertOrderDetail(orderDetail).let {
                if (it > 0) {
                    _isComplete.postValue(true)
                }
            }
        }
    }
}