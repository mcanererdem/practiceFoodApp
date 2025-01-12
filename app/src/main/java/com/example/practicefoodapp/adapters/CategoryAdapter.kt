package com.example.practicefoodapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.practicefoodapp.databinding.CategoryItemLayoutBinding
import com.example.practicefoodapp.model.FoodCategory

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    inner class CategoryViewHolder(
        private val binding: CategoryItemLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: FoodCategory) {
            binding.apply {
                tvCategoryImage.text = category.strCategory
            }
        }
    }

    lateinit var itemClicked: (FoodCategory) -> Unit
    private var categoryList = emptyList<FoodCategory>()

    fun setCategoryList(categoryList: List<FoodCategory>) {
        this.categoryList = categoryList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryViewHolder {
        return CategoryViewHolder(
            CategoryItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: CategoryViewHolder,
        position: Int
    ) {
        val currentCategory = categoryList[position]
        holder.bind(currentCategory)
        holder.itemView.setOnClickListener {
            itemClicked(currentCategory)
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

}