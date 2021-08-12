package com.sarftec.lifequotes.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sarftec.lifequotes.data.model.Category
import com.sarftec.lifequotes.databinding.LayoutCategoryItemBinding

class CategoryAdapter(
    private var items: List<Category> = emptyList(),
    private val onClick: (Category) -> Unit
) : RecyclerView.Adapter<CategoryHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        val layoutBinding = LayoutCategoryItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CategoryHolder(layoutBinding, onClick)
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun submitData(items: List<Category>) {
        this.items = items
        notifyDataSetChanged()
    }
}