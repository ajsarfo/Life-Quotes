package com.sarftec.lifequotes.presentation.activity

import android.os.Bundle
import androidx.activity.viewModels
import com.sarftec.lifequotes.presentation.adapter.QuoteAdapter
import com.sarftec.lifequotes.presentation.advertisement.InterstitialManager
import com.sarftec.lifequotes.presentation.viewmodel.QuoteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuoteActivity : BaseQuoteActivity() {

    override val viewModel by viewModels<QuoteViewModel>()

    private val interstitialManager by lazy {
        InterstitialManager(
            this,
            networkManager,
            listOf(2, 3, 3)
        )
    }

    override fun createAdapter(): QuoteAdapter {
        return QuoteAdapter(dependency,viewModel) { quote ->
            interstitialManager.showAd {
                if (viewModel.isInspirationalQuotes()) {
                    navigateTo(
                        DetailActivity::class.java,
                        bundle = Bundle().apply {
                            putString(NAVIGATION_ROOT, NAVIGATION_MAIN)
                            putInt(QUOTE_SELECTED_ID, quote.id)
                            putInt(RANDOM_SEED, viewModel.getSeed())
                        }
                    )
                } else {
                    navigateTo(
                        DetailActivity::class.java,
                        bundle = Bundle().apply {
                            putInt(CATEGORY_SELECTED_ID, quote.categoryId)
                            putInt(QUOTE_SELECTED_ID, quote.id)
                            putString(NAVIGATION_ROOT, NAVIGATION_QUOTE_LIST)
                            putInt(RANDOM_SEED, viewModel.getSeed())
                        }
                    )
                }
            }
        }
    }
}