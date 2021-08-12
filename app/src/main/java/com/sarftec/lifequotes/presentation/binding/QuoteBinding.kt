package com.sarftec.lifequotes.presentation.binding

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.sarftec.lifequotes.BR
import com.sarftec.lifequotes.Dependency
import com.sarftec.lifequotes.R
import com.sarftec.lifequotes.data.model.Quote
import com.sarftec.lifequotes.presentation.file.bindable
import com.sarftec.lifequotes.presentation.file.copy
import com.sarftec.lifequotes.presentation.file.share
import com.sarftec.lifequotes.presentation.file.toast
import com.sarftec.lifequotes.presentation.image.ImageHolder
import com.sarftec.lifequotes.presentation.viewmodel.BaseQuoteViewModel

class QuoteBinding(
    private val quote: Quote,
    private val dependency: Dependency,
    private val viewModel: BaseQuoteViewModel,
    private val onClick: (Quote) -> Unit
) : BaseObservable() {

    val message = quote.message

    @get:Bindable
    var favoriteIcon by bindable(getFavorite(quote), BR.favoriteIcon)

    fun clicked() {
       onClick(quote)
    }

    private fun getFavorite(quote: Quote): ImageHolder {
        return ImageHolder.ImageDrawable(
            if (quote.isFavorite) R.drawable.ic_love_filled
            else R.drawable.ic_love_unfilled
        )
    }

    fun onFavorite() {
        quote.isFavorite = !quote.isFavorite
        favoriteIcon = getFavorite(quote)
        viewModel.saveQuoteFavorite(quote)
        dependency.context.apply {
            toast(if(quote.isFavorite) "Added to bookmarks" else "Removed from bookmarks")
        }
    }

    fun onShare() {
        dependency.context.apply {
            share(quote.message, "Copy")
        }
    }

    fun onCopy() {
        dependency.context.apply {
            copy(quote.message, "label")
            toast("Copied to clipboard")
        }
    }

    fun onSave() {

    }
}