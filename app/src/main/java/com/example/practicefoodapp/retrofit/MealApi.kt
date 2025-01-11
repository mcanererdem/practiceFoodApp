package com.example.practicefoodapp.retrofit

import com.example.practicefoodapp.model.MealList
import retrofit2.Call
import retrofit2.http.GET

interface MealApi {

    @GET("random.php")
    fun getRandomMeal(): Call<MealList>

}