package com.example.practicefoodapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.practicefoodapp.databinding.PopulerFoodItemBinding
import com.example.practicefoodapp.model.Meal

class PopularFoodAdapter : RecyclerView.Adapter<PopularFoodAdapter.PopularFoodViewHolder>() {
    inner class PopularFoodViewHolder(val binding: PopulerFoodItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(meal: Meal) {
            binding.apply {
                Glide.with(binding.root).load(meal.strMealThumb).into(binding.popularItemFoodImage)
            }
        }
    }

    var onClicked: (Meal) -> Unit = {}
    private var popularMealList = emptyList<Meal>()

    fun setPopularMealList(mealList: List<Meal>) {
        this.popularMealList = mealList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PopularFoodViewHolder {
        return PopularFoodViewHolder(
            PopulerFoodItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: PopularFoodViewHolder,
        position: Int
    ) {
        val currentMeal = popularMealList[position]

        holder.bind(currentMeal)
        holder.itemView.setOnClickListener {
            onClicked(currentMeal)
        }
    }

    override fun getItemCount(): Int {
        return popularMealList.size
    }
}