package com.sarftec.lifequotes.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sarftec.lifequotes.Dependency
import com.sarftec.lifequotes.data.model.Quote
import com.sarftec.lifequotes.databinding.LayoutQuoteItemBinding
import com.sarftec.lifequotes.presentation.viewmodel.BaseQuoteViewModel

class QuoteAdapter(
    private val dependency: Dependency,
    private val viewModel: BaseQuoteViewModel,
    private var items: List<Quote> = emptyList(),
    private val onClick: (Quote) -> Unit
) : RecyclerView.Adapter<QuoteHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteHolder {
        val layoutBinding = LayoutQuoteItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return QuoteHolder(layoutBinding, dependency, viewModel, onClick)
    }

    override fun onBindViewHolder(holder: QuoteHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun resetQuoteFavorites(indexedFavorites: Set<Map.Entry<Int, Boolean>>) {
        indexedFavorites.forEach {
            items[it.key].isFavorite = it.value
            notifyItemChanged(it.key)
        }
    }
    fun submitData(items: List<Quote>) {
        this.items = items
        notifyDataSetChanged()
    }
}