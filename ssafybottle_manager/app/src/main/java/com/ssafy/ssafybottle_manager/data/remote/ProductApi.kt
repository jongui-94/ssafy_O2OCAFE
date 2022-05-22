package com.ssafy.ssafybottle_manager.data.remote

import com.ssafy.ssafybottle_manager.data.dto.product.ProductDto
import retrofit2.Response
import retrofit2.http.GET

interface ProductApi {
    @GET("product")
    suspend fun getProducts() : Response<List<ProductDto>>

    @GET("product/beverage")
    suspend fun getBeverage() : Response<List<ProductDto>>

    @GET("product/dessert")
    suspend fun getDessert() : Response<List<ProductDto>>
}