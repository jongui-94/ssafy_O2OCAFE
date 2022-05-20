package com.ssafy.smartstore.ui.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.smartstore.base.BaseViewModel
import com.ssafy.smartstore.data.dto.product.ProductCommentDto
import com.ssafy.smartstore.data.dto.product.ProductDetailDto
import com.ssafy.smartstore.data.repository.Repository
import kotlinx.coroutines.launch

class OrderViewModel : BaseViewModel() {
    private val repository: Repository = Repository.get()

    private val _product = MutableLiveData<ProductDetailDto>()
    val product: LiveData<ProductDetailDto> get() = _product

    private val _comments = MutableLiveData<List<ProductCommentDto>>()
    val comments: LiveData<List<ProductCommentDto>> get() = _comments

    private val _isDeleted = MutableLiveData<Boolean>()
    val isDeleted: LiveData<Boolean> get() = _isDeleted

    fun getProduct(productId: Int) {
        viewModelScope.launch(exceptionHandler) {
            repository.getProductDetail(productId).let { response ->
                if (response.isSuccessful) {
                    response.body()?.let {
                        val productDetail = it[0].toProductDetailDto()
                        _product.postValue(productDetail)

                        // 코멘트 있으면
                        if (productDetail.commentCnt > 0) {
                            val productComments: List<ProductCommentDto> =
                                it.map { productDetailResponse ->
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
                if (it.isSuccessful) {
                    _isDeleted.postValue(true)
                } else {
                    _isDeleted.postValue(false)
                }
            }
        }
    }
}