package com.example.practicefoodapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicefoodapp.model.CategoryList
import com.example.practicefoodapp.model.FoodCategory
import com.example.practicefoodapp.model.Meal
import com.example.practicefoodapp.model.MealList
import com.example.practicefoodapp.retrofit.RetrofitInst
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {
    private val randomMealLiveData = MutableLiveData<Meal>()
    val observeRandomMeal = randomMealLiveData as LiveData<Meal>

    private val popularMealLiveData = MutableLiveData<List<Meal>>()
    val observePopularMeal = popularMealLiveData as LiveData<List<Meal>>

    private val allcategoriesLiveData = MutableLiveData<List<FoodCategory>>()
    val observeAllCategories = allcategoriesLiveData as LiveData<List<FoodCategory>>

    fun getAllCategories() {
        viewModelScope.launch {
            RetrofitInst.api.gelAllCategories().enqueue(object : Callback<CategoryList> {
                override fun onResponse(
                    p0: Call<CategoryList?>,
                    p1: Response<CategoryList?>
                ) {
                    allcategoriesLiveData.value = p1.body()!!.meals
                }

                override fun onFailure(
                    p0: Call<CategoryList?>,
                    p1: Throwable
                ) {
                    Log.e("HomeViewModel", "onFailure: getAllCategories")
                }
            })
        }
    }

    fun getRandomMeal() {
        RetrofitInst.api.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(
                p0: Call<MealList?>, response: Response<MealList?>
            ) {
                if (response.body() != null && response.body()!!.meals.isNotEmpty()) {
                    val randomMeal = response.body()!!.meals[0]
                    randomMealLiveData.value = randomMeal
                }
            }

            override fun onFailure(p0: Call<MealList?>, p1: Throwable) {}
        })
    }

    fun getPopularMealsByArea(areaName: String = "Turkish") {
        Log.e("HomeViewModel", "onResponse: Started")
        viewModelScope.launch {
            RetrofitInst.api.getPopularItems(areaName).enqueue(object : Callback<MealList> {
                override fun onResponse(
                    p0: Call<MealList?>,
                    p1: Response<MealList?>
                ) {
                    if (p1.isSuccessful && p1.body() != null) {
                        popularMealLiveData.value = p1.body()!!.meals
                        Log.e("HomeViewModel", "onResponse: ${p1.body()!!.meals}")
                    }
                }

                override fun onFailure(
                    p0: Call<MealList?>,
                    p1: Throwable
                ) {
                    Log.e("HomeViewModel", "onFailure: getPopularMealsByArea")
                }
            })
        }
    }
}