package com.sarftec.lifequotes.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import com.sarftec.lifequotes.data.model.Quote
import com.sarftec.lifequotes.databinding.LayoutQuotePagerViewBinding

class DetailViewHolder(
    private val layoutBinding: LayoutQuotePagerViewBinding
) : RecyclerView.ViewHolder(layoutBinding.root) {

    fun bind(quote: Quote) {
        layoutBinding.message.text = quote.message
    }
}