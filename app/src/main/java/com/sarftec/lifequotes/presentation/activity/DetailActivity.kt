package com.sarftec.lifequotes.presentation.activity

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.viewpager2.widget.ViewPager2
import com.appodeal.ads.Appodeal
import com.sarftec.lifequotes.BR
import com.sarftec.lifequotes.R
import com.sarftec.lifequotes.data.model.Quote
import com.sarftec.lifequotes.databinding.ActivityDetailBinding
import com.sarftec.lifequotes.presentation.adapter.DetailAdapter
import com.sarftec.lifequotes.presentation.file.bindable
import com.sarftec.lifequotes.presentation.file.copy
import com.sarftec.lifequotes.presentation.file.share
import com.sarftec.lifequotes.presentation.file.toast
import com.sarftec.lifequotes.presentation.image.ImageHolder
import com.sarftec.lifequotes.presentation.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : BaseActivity() {

    private val binding by lazy {
        ActivityDetailBinding.inflate(
            layoutInflater
        )
    }

    private val viewModel by viewModels<DetailViewModel>()

    private val detailAdapter by lazy {
        DetailAdapter()
    }

    override fun onResume() {
        super.onResume()
        Appodeal.show(this, Appodeal.BANNER_VIEW)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.apply {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            }
        }
        setContentView(binding.root)
        Appodeal.setBannerViewId(R.id.main_banner)
        intent.getBundleExtra(ACTIVITY_BUNDLE)?.let {
            viewModel.setBundle(it)
        }
        val anchor = DetailAnchor()
        binding.binding = anchor
        binding.executePendingBindings()
        setupPager()
        viewModel.fetch()
        viewModel.indexedQuotes.observe(this) {
            detailAdapter.submitData(it.quotes)
            binding.viewPager.setCurrentItem(it.index, false)
        }
        viewModel.currentQuote.observe(this) {
            anchor.setQuote(it)
        }
    }

    private fun setupPager() {
        binding.viewPager.adapter = detailAdapter
        binding.viewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    viewModel.setCurrentQuoteIndex(position)
                    super.onPageSelected(position)
                }
            }
        )
    }

    /**
     * Inner class for changing bottom anchor behaviour
     */
    inner class DetailAnchor() : BaseObservable() {

        @get:Bindable
        var favoriteIcon: ImageHolder by bindable(ImageHolder.Empty, BR.favoriteIcon)

        private fun getFavorite(quote: Quote): ImageHolder {
            return ImageHolder.ImageDrawable(
                if (quote.isFavorite) R.drawable.ic_love_filled
                else R.drawable.ic_love_unfilled
            )
        }

        fun setQuote(quote: Quote) {
            favoriteIcon = getFavorite(quote)
        }

        fun onFavorite() {
            viewModel.getCurrentQuote()?.let {
                toast(if(!it.isFavorite) "Added to bookmarks" else "Removed from bookmarks")
            }
            viewModel.changeCurrentQuoteFavorite()
        }

        fun onShare() {
           viewModel.getCurrentQuote()?.let {
               dependency.context.apply {
                   share(it.message, "Copy")
               }
           }
        }

        fun onCopy() {
           viewModel.getCurrentQuote()?.let {
               dependency.context.apply {
                   copy(it.message, "label")
                   toast("Copied to clipboard")
               }
           }
        }

        fun onSave() {

        }
    }
}