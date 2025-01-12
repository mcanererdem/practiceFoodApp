package com.example.practicefoodapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.example.practicefoodapp.activities.MealActivity
import com.example.practicefoodapp.adapters.CategoryAdapter
import com.example.practicefoodapp.adapters.PopularFoodAdapter
import com.example.practicefoodapp.databinding.FragmentHomeBinding
import com.example.practicefoodapp.model.Meal
import com.example.practicefoodapp.viewmodel.HomeViewModel
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var myRandomMeal: Meal
    private lateinit var popularFoodAdapter: PopularFoodAdapter
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        popularFoodAdapter = PopularFoodAdapter()
        popularFoodAdapter.onClicked = { meal ->
            myRandomMeal = meal
            imageClicked()
        }
        binding.rvMostLikedFoods.apply {
            adapter = popularFoodAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            LinearSnapHelper().attachToRecyclerView(this)
        }


        categoryAdapter = CategoryAdapter()
        categoryAdapter.itemClicked = { category ->
            Toast.makeText(context, "${category.strCategory} Clicked!", Toast.LENGTH_SHORT).show()
        }
        binding.rvCategories.apply {
            adapter = categoryAdapter
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL)
            setHasFixedSize(true)
        }


        lifecycleScope.launch {
            observeRandomMeal()
        }
        observePopularMeals()
        observeCategories()
        binding.ivRandomImage.setOnClickListener {
            imageClicked()
        }

        return binding.root
    }

    private fun observeCategories() {
        viewModel.getAllCategories()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.observeAllCategories.observe(viewLifecycleOwner) {
                    categoryAdapter.setCategoryList(it)
                }
            }
        }
    }

    private fun observePopularMeals() {
        viewModel.getPopularMealsByArea("Unknown")

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.observePopularMeal.observe(viewLifecycleOwner) {
                    popularFoodAdapter.setPopularMealList(it)
                }
            }
        }
    }

    private fun imageClicked() {
        startActivity(
            Intent(
                requireContext(), MealActivity::class.java
            ).putExtra("mealId", myRandomMeal.idMeal)
                .putExtra("mealName", myRandomMeal.strMeal)
                .putExtra("mealThumb", myRandomMeal.strMealThumb)
        )
    }


    suspend fun observeRandomMeal() {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.getRandomMeal()

            viewModel.observeRandomMeal.observe(viewLifecycleOwner) { randomMeal ->
                myRandomMeal = randomMeal
                Glide.with(this@HomeFragment).load(myRandomMeal.strMealThumb)
                    .into(binding.ivRandomImage)
            }
        }
    }
}