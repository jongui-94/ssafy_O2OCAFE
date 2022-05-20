package com.ssafy.smartstore.data.remote

import com.ssafy.smartstore.data.dto.product.ProductDetailResponseDto
import com.ssafy.smartstore.data.dto.product.ProductDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApi {
    @GET("product")
    suspend fun getProducts() : Response<List<ProductDto>>

    @GET("product/beverage")
    suspend fun getBeverage() : Response<List<ProductDto>>

    @GET("product/dessert")
    suspend fun getDessert() : Response<List<ProductDto>>

    @GET("product/{productId}")
    suspend fun getProductDetail(@Path("productId") productId: Int) : Response<List<ProductDetailResponseDto>>

    @GET("product/recommendByRating")
    suspend fun getProductRecommend() : Response<List<ProductDto>>

    @GET("product/recommendBySell")
    suspend fun getProductTop10() : Response<List<ProductDto>>
}

