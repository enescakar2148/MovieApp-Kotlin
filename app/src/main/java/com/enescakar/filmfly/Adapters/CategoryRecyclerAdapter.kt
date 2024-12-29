package com.enescakar.filmfly.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.enescakar.filmfly.Model.Category
import com.enescakar.filmfly.Model.Movie
import com.enescakar.filmfly.databinding.CategoryRecyclerItemBinding

class CategoryRecyclerAdapter(val categoryList: ArrayList<Category>): RecyclerView.Adapter<CategoryRecyclerAdapter.MyHolder>() {
    class MyHolder(val binding: CategoryRecyclerItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val binding = CategoryRecyclerItemBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return MyHolder(binding)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.binding.categoryText.text = categoryList.get(position).name
        holder.binding.categoryText.setOnClickListener { item->
            Toast.makeText(
                holder.binding.categoryText.context,
                "Category Clicked: ${categoryList.get(position)}",
                Toast.LENGTH_SHORT)
                .show()
        }
    }
    fun updateCategoryAdapter(newCountryList: List<Category>) {
        categoryList.clear()
        categoryList.addAll(newCountryList)
        notifyDataSetChanged()
    }
}