package com.example.retrofit_post.utils

import com.example.retrofit_post.RandomDogInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RandomDogInstance {
    val api: RandomDogInterface by lazy {
        Retrofit.Builder()
            .baseUrl(Util.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RandomDogInterface::class.java)
    }
}