package com.enescakar.filmfly.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.nurcanyildirim.test3.R
import com.nurcanyildirim.test3.model.Category

class CategoryAdapter(private val onCategoryClick: (Category) -> Unit) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    private var categories: List<Category> = emptyList()

    fun updateCategories(newCategories: List<Category>) {
        categories = newCategories
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view, onCategoryClick)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount(): Int = categories.size

    class CategoryViewHolder(
        itemView: View,
        private val onCategoryClick: (Category) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        private val nameText: TextView = itemView.findViewById(R.id.categoryName)

        fun bind(category: Category) {
            nameText.text = category.name
            
            // Update background based on selection state
            val backgroundRes = if (category.isSelected) {
                R.drawable.bg_category_selected
            } else {
                R.drawable.bg_category_normal
            }
            itemView.setBackgroundResource(backgroundRes)

            // Update text color based on selection state
            val textColor = if (category.isSelected) {
                ContextCompat.getColor(itemView.context, R.color.black)
            } else {
                ContextCompat.getColor(itemView.context, R.color.category_text)
            }
            nameText.setTextColor(textColor)

            itemView.setOnClickListener {
                onCategoryClick(category)
            }
        }
    }
} 