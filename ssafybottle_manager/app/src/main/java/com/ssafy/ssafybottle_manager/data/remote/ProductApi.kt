package com.ssafy.ssafybottle_manager.data.remote

import com.ssafy.ssafybottle_manager.data.dto.product.ProductDetailResponseDto
import com.ssafy.ssafybottle_manager.data.dto.product.ProductDto
import com.ssafy.ssafybottle_manager.data.dto.product.ProductSalesDto
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

    @GET("product/salesAll")
    suspend fun getTotalSales() : Response<Int>

    @GET("product/salesOne")
    suspend fun getProductSales() : Response<List<ProductSalesDto>>

    @GET("product/{productId}")
    suspend fun getProductDetail(@Path("productId") productId: Int) : Response<List<ProductDetailResponseDto>>
}