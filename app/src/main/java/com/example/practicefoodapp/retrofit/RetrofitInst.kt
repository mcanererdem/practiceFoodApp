package com.example.practicefoodapp.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInst {
    private val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"

    private val client = OkHttpClient.Builder().writeTimeout(3_000L, TimeUnit.SECONDS)
        .readTimeout(3_000L, TimeUnit.SECONDS).connectTimeout(3_000L, TimeUnit.SECONDS).build()

    private val retrofit = Retrofit.Builder().baseUrl(BASE_URL).client(client)
        .addConverterFactory(GsonConverterFactory.create()).build()

    val api = retrofit.create(MealApi::class.java)
}