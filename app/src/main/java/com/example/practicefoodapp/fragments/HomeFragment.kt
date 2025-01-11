package com.example.practicefoodapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.practicefoodapp.databinding.FragmentHomeBinding
import com.example.practicefoodapp.model.MealList
import com.example.practicefoodapp.retrofit.RetrofitInst
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        RetrofitInst.api.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(
                p0: Call<MealList?>,
                response: Response<MealList?>
            ) {
                if (response.body() != null && response.body()!!.meals.isNotEmpty()) {
                    val randomMeal = response.body()!!.meals[0]
                    Glide.with(this@HomeFragment)
                        .load(randomMeal.strMealThumb)
                        .into(binding.ivRandomImage)
                }

            }

            override fun onFailure(
                p0: Call<MealList?>,
                p1: Throwable
            ) {
                Toast.makeText(context, p1.message, Toast.LENGTH_SHORT).show()
            }

        })
        return binding.root
    }
}