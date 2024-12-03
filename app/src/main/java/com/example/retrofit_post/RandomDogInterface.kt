package com.example.retrofit_post

import com.example.retrofit_post.model.ApiData
import retrofit2.Response
import retrofit2.http.GET

interface RandomDogInterface {
    @GET("woof.json?ref=apilist.fun")
    suspend fun getRandomDog(): Response<ApiData>
}