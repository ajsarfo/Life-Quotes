package com.sarftec.lifequotes.presentation.activity

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.appodeal.ads.Appodeal
import com.sarftec.lifequotes.R
import com.sarftec.lifequotes.databinding.ActivityQuoteBinding
import com.sarftec.lifequotes.presentation.adapter.QuoteAdapter
import com.sarftec.lifequotes.presentation.viewmodel.BaseQuoteViewModel

abstract class BaseQuoteActivity : BaseActivity() {

    protected abstract val viewModel : BaseQuoteViewModel

    private lateinit var quoteAdapter: QuoteAdapter

    protected abstract fun createAdapter() : QuoteAdapter

    protected val binding by lazy {
        ActivityQuoteBinding.inflate(
            layoutInflater
        )
    }

    override fun onResume() {
        super.onResume()
        Appodeal.show(this, Appodeal.BANNER_VIEW)
        quoteAdapter.resetQuoteFavorites(modifiedQuoteList.entries)
        modifiedQuoteList.clear()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Appodeal.setBannerViewId(R.id.main_banner)
        intent.getBundleExtra(ACTIVITY_BUNDLE)?.let {
            viewModel.setBundle(it)
        }
        binding.title.text = viewModel.toolbarTitle()
        binding.back.setOnClickListener {
            onBackPressed()
        }
        quoteAdapter = createAdapter()
        configureAdapter()
        viewModel.fetch()
        viewModel.quotes.observe(this) {
            quoteAdapter.submitData(it)
        }
    }

    private fun configureAdapter() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@BaseQuoteActivity)
            setHasFixedSize(true)
            adapter = quoteAdapter
        }
    }
}