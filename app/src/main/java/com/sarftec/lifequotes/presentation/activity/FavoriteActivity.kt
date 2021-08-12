package com.sarftec.lifequotes.presentation.activity

import android.os.Bundle
import androidx.activity.viewModels
import com.sarftec.lifequotes.presentation.adapter.QuoteAdapter
import com.sarftec.lifequotes.presentation.advertisement.InterstitialManager
import com.sarftec.lifequotes.presentation.viewmodel.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteActivity : BaseQuoteActivity() {

    override val viewModel by viewModels<FavoriteViewModel>()

    private val interstitialManager by lazy {
        InterstitialManager(
            this,
            networkManager,
            listOf(3, 2)
        )
    }

    override fun createAdapter(): QuoteAdapter {
        return QuoteAdapter(dependency, viewModel) { quote ->
            interstitialManager.showAd {
                navigateTo(
                    DetailActivity::class.java,
                    bundle = Bundle().apply {
                        putInt(QUOTE_SELECTED_ID, quote.id)
                        putString(NAVIGATION_ROOT, NAVIGATION_FAVORITE_LIST)
                    }
                )
            }
        }
    }
}