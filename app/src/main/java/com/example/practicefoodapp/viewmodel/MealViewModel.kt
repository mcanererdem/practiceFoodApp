package com.example.practicefoodapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicefoodapp.model.Meal
import com.example.practicefoodapp.model.MealList
import com.example.practicefoodapp.retrofit.RetrofitInst
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel: ViewModel() {
    private val _randomMeal = MutableLiveData<Meal>()
    val randomMeal: LiveData<Meal> = _randomMeal

    fun getRandomMeal(id:String) {
        viewModelScope.launch {
            delay(300L)
            RetrofitInst.api.getMealById(id).enqueue(object : Callback<MealList> {
                override fun onResponse(
                    p0: Call<MealList?>,
                    p1: Response<MealList?>
                ) {
                    if (p1.body() != null && p1.isSuccessful) {
                        _randomMeal.value = p1.body()!!.meals[0]
                    }
                }

                override fun onFailure(
                    p0: Call<MealList?>,
                    p1: Throwable
                ) {
                    Log.d("MealViewModel -> ", "onFailure: getRandomMeal() hatası")
                }

            })
        }
    }
}