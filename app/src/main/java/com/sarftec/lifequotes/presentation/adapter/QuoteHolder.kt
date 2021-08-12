package com.sarftec.lifequotes.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import com.sarftec.lifequotes.Dependency
import com.sarftec.lifequotes.data.model.Quote
import com.sarftec.lifequotes.databinding.LayoutQuoteItemBinding
import com.sarftec.lifequotes.presentation.binding.QuoteBinding
import com.sarftec.lifequotes.presentation.viewmodel.BaseQuoteViewModel

class QuoteHolder(
    private val layoutBinding: LayoutQuoteItemBinding,
    private val dependency: Dependency,
    private val viewModel: BaseQuoteViewModel,
    private val listener: (Quote) -> Unit
) : RecyclerView.ViewHolder(layoutBinding.root) {

    fun bind(quote: Quote) {
        layoutBinding.binding = QuoteBinding(quote, dependency, viewModel, listener)
        layoutBinding.executePendingBindings()
    }
}