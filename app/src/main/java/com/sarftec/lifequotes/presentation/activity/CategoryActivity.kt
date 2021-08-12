package com.sarftec.lifequotes.presentation.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.appodeal.ads.Appodeal
import com.sarftec.lifequotes.R
import com.sarftec.lifequotes.databinding.ActivityCategoryBinding
import com.sarftec.lifequotes.presentation.adapter.CategoryAdapter
import com.sarftec.lifequotes.presentation.advertisement.InterstitialManager
import com.sarftec.lifequotes.presentation.viewmodel.CategoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryActivity : BaseActivity() {

    private val binding by lazy {
        ActivityCategoryBinding.inflate(
            layoutInflater
        )
    }

    private val interstitialManager by lazy {
        InterstitialManager(
            this,
            networkManager,
            listOf(3, 2, 4, 3)
        )
    }

    private val categoryAdapter by lazy {
        CategoryAdapter { category ->
           interstitialManager.showAd {
               navigateTo(
                   QuoteActivity::class.java,
                   bundle = Bundle().also {
                       it.putInt(CATEGORY_SELECTED_ID, category.id)
                       it.putString(CATEGORY_SELECTED_NAME, category.name)
                   }
               )
           }
        }
    }

    private val viewModel by viewModels<CategoryViewModel>()

    override fun onResume() {
        super.onResume()
        Appodeal.show(this, Appodeal.BANNER_VIEW)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Appodeal.setBannerViewId(R.id.main_banner)
        binding.navBack.setOnClickListener {
            onBackPressed()
        }
        viewModel.fetch()
        viewModel.categories.observe(this) {
            categoryAdapter.submitData(it)
        }
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(this@CategoryActivity, 2)
            adapter = categoryAdapter
            setHasFixedSize(true)
        }
    }
}