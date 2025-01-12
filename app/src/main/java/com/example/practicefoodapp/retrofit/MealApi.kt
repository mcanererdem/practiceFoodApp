package com.example.practicefoodapp.retrofit

import com.example.practicefoodapp.model.CategoryList
import com.example.practicefoodapp.model.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    @GET("random.php")
    fun getRandomMeal(): Call<MealList>

    @GET("lookup.php?")
    fun getMealById(
        @Query("i") id: String
    ): Call<MealList>

    @GET("filter.php?")
    fun getPopularItems(
        @Query("a") areaName: String
    ): Call<MealList>

    @GET("list.php")
    fun gelAllCategories(
        @Query("c") categoryName: String = "list"
    ): Call<CategoryList>
}