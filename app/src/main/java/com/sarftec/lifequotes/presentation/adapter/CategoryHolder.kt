package com.sarftec.lifequotes.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import com.sarftec.lifequotes.data.model.Category
import com.sarftec.lifequotes.databinding.LayoutCategoryItemBinding

class CategoryHolder(
    private val layoutBinding: LayoutCategoryItemBinding,
    private val onClick: (Category) -> Unit
) : RecyclerView.ViewHolder(layoutBinding.root) {

    private var category: Category? = null

    init {
        layoutBinding.categoryCard.setOnClickListener { _ ->
            category?.let {
               onClick(it)
            }
        }
    }
    fun bind(category: Category) {
        this.category = category
        layoutBinding.category.text = category.name
    }
}