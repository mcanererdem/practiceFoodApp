package com.example.practicefoodapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.example.practicefoodapp.databinding.ActivityMealBinding
import com.example.practicefoodapp.model.Meal
import com.example.practicefoodapp.viewmodel.MealViewModel
import kotlinx.coroutines.launch

class MealActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMealBinding
    private var myRandomMeal: Meal? = null
    private lateinit var mealViewModel: MealViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        mealViewModel = MealViewModel()

        getRandomMeal()
        observeRandomMeal()
        onYoutubeImageClicked()
    }

    private fun onYoutubeImageClicked() {
        binding.ivYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, myRandomMeal?.strYoutube?.toUri())
            startActivity(intent)
        }
    }

    private fun observeRandomMeal() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mealViewModel.randomMeal.observe(this@MealActivity) { meal ->
                    stopLoading()
                    myRandomMeal = meal
                    binding.apply {
                        tvCategoryMealActivity.text = meal.strCategory
                        tvLocationMealActivity.text = meal.strArea
                        tvInstructionMealActivity.text = meal.strMeal
                        tvBodyMealActivity.text = meal.strInstructions
                    }
                }
            }
        }
    }

    private fun getRandomMeal() {
        val mealId = intent.getStringExtra("mealId") ?: ""
        val mealName = intent.getStringExtra("mealName") ?: ""
        val mealThumb = intent.getStringExtra("mealThumb") ?: ""
        if (!mealId.equals("") && !mealName.equals("") && !mealThumb.equals("")) {
            binding.apply {
                tvCategoryMealActivity.text = "Category"
                tvLocationMealActivity.text = "Palestian"
                tvInstructionMealActivity.text = mealName
                Glide.with(this@MealActivity).load(mealThumb).into(ivCollapsingImage)
            }
        }
        mealViewModel.getRandomMeal(mealId)
        startLoading()
    }

    private fun startLoading() {
        binding.apply {
            progressBar.visibility = View.VISIBLE
            tvCategoryMealActivity.visibility = View.INVISIBLE
            tvLocationMealActivity.visibility = View.INVISIBLE
            tvInstructionMealActivity.visibility = View.INVISIBLE
        }
    }

    private fun stopLoading() {
        binding.apply {
            progressBar.visibility = View.INVISIBLE
            tvCategoryMealActivity.visibility = View.VISIBLE
            tvLocationMealActivity.visibility = View.VISIBLE
            tvInstructionMealActivity.visibility = View.VISIBLE
        }
    }
}