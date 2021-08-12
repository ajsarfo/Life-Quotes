package com.sarftec.lifequotes.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sarftec.lifequotes.data.model.Quote
import com.sarftec.lifequotes.databinding.LayoutQuotePagerViewBinding

class DetailAdapter(
    private var items: List<Quote> = emptyList()
) : RecyclerView.Adapter<DetailViewHolder>() {

    fun submitData(items: List<Quote>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
       val layoutBinding = LayoutQuotePagerViewBinding.inflate(
           LayoutInflater.from(parent.context),
           parent,
           false
       )
        return DetailViewHolder(layoutBinding)
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
       holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}