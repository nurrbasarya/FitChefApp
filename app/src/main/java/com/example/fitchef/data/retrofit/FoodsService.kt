package com.example.fitchef.data.retrofit

import com.example.fitchef.common.Constants.BEST_SELLERS
import com.example.fitchef.common.Constants.FOODS
import com.example.fitchef.data.model.FoodsResponse
import retrofit2.Response
import retrofit2.http.GET

interface FoodsService {

    @GET(FOODS)
    suspend fun allFoods(): Response<FoodsResponse>

    @GET(BEST_SELLERS)
    suspend fun bestSellers(): Response<FoodsResponse>

}