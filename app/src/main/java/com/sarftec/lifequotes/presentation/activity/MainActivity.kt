package com.sarftec.lifequotes.presentation.activity

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import coil.load
import com.appodeal.ads.Appodeal
import com.sarftec.lifequotes.R
import com.sarftec.lifequotes.data.model.Category
import com.sarftec.lifequotes.databinding.ActivityMainBinding
import com.sarftec.lifequotes.databinding.LayoutMainSectionBinding
import com.sarftec.lifequotes.databinding.LayoutMainTodayQuoteBinding
import com.sarftec.lifequotes.databinding.LayoutPaletteItemBinding
import com.sarftec.lifequotes.presentation.advertisement.InterstitialManager
import com.sarftec.lifequotes.presentation.file.*
import com.sarftec.lifequotes.presentation.image.CoilHolder
import com.sarftec.lifequotes.presentation.image.ImageStore
import com.sarftec.lifequotes.presentation.manager.AppReviewManager
import com.sarftec.lifequotes.presentation.viewmodel.MainViewModel
import com.sarftec.lifequotes.presentation.viewmodel.Section
import com.sarftec.lifequotes.presentation.viewmodel.Today
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModels<MainViewModel>()

    private var drawerCallback: (() -> Unit)? = null

    private val toggle by lazy {
        ActionBarDrawerToggle(
            this,
            binding.navDrawer,
            R.string.open_drawer,
            R.string.close_drawer
        )
    }

    private val interstitialManager by lazy {
        InterstitialManager(
            this,
            networkManager,
            listOf(1, 3, 4, 3)
        )
    }

    override fun onResume() {
        super.onResume()
        Appodeal.show(this, Appodeal.BANNER_VIEW)
        modifiedQuoteList.clear()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        /**************Set up appodeal configuration*****************/
        Appodeal.setBannerViewId(R.id.main_banner)
        Appodeal.initialize(
            this,
            getString(R.string.appodeal_id),
            Appodeal.BANNER_VIEW or Appodeal.INTERSTITIAL
        )
        /*************************************************************/
        lifecycleScope.launchWhenCreated {
            savedInstanceState ?: editSettings(
                AppReviewManager.App_START_UP_TIMES,
                readSettings(AppReviewManager.App_START_UP_TIMES, 0).first() + 1
            )
        }
        setSupportActionBar(binding.mainToolbar)
        binding.mainToolbar.setNavigationOnClickListener {
            binding.navDrawer.openDrawer(
                GravityCompat.START
            )
        }
        setupNavigationDrawer()
        setupNavigationView()
        configurePalette()
        configureSections()
        viewModel.fetch()
        viewModel.mainSection.observe(this) { sections ->
            binding.apply {
                setSection(layoutFirstSection, sections.first)
                setSection(layoutSecondSection, sections.second)
                setToday(layoutToday, sections.today)
            }
        }
        viewModel.paletteIcons.observe(this) { icons ->
            setIcon(binding.layoutPalette.first, icons.rate)
            setIcon(binding.layoutPalette.second, icons.share)
            setIcon(binding.layoutPalette.third, icons.moreApps)
        }
        with(AppReviewManager(this@MainActivity)) {
            init()
            lifecycleScope.launchWhenCreated {
                triggerReview()
            }
        }
    }

    private fun configureSections() {
        binding.layoutSecondSection.mainSection.setOnClickListener {
            interstitialManager.showAd {
                navigateTo(CategoryActivity::class.java)
            }
        }
        binding.layoutFirstSection.mainSection.setOnClickListener {
           interstitialManager.showAd {
               navigateTo(
                   QuoteActivity::class.java,
                   bundle = Bundle().also {
                       it.putInt(CATEGORY_SELECTED_ID, Category.CATEGORY_INSPIRATION_ID)
                       it.putString(INSPIRATIONAL_MARKER, IS_INSPIRATIONAL_QUOTES)
                       it.putString(CATEGORY_SELECTED_NAME, Category.CATEGORY_INSPIRATION)
                   }
               )
           }
        }
    }

    private fun setIcon(layout: LayoutPaletteItemBinding, coilHolder: CoilHolder) {
        if(coilHolder is CoilHolder.DrawableHolder) layout.icon.setImageResource(
            coilHolder.id
        )
    }

    private fun setSection(layout: LayoutMainSectionBinding, section: Section) {
        layout.title.text = section.title
        if (section.image is CoilHolder.UriHolder) layout.image.load(
            section.image.uri
        )
    }

    private fun setToday(layout: LayoutMainTodayQuoteBinding, today: Today) {
        layout.quote.text = today.message
        if (today.icon is CoilHolder.UriHolder) layout.icon.load(today.icon.uri)
    }

    override fun onBackPressed() {
        if (binding.navDrawer.isDrawerOpen(GravityCompat.START)) {
            binding.navDrawer.closeDrawer(GravityCompat.START)
        } else finish()
    }

    private fun configurePalette() {
        binding.layoutPalette.first.icon.setOnClickListener {
            rateApp()
        }
        binding.layoutPalette.second.icon.setOnClickListener {
            viewModel.getTodayMessage()?.let { message ->
                dependency.context.apply {
                    share(message, "Copy")
                }
            }
        }
        binding.layoutPalette.third.icon.setOnClickListener {
            moreApps()
        }
    }

    private fun setupNavigationView() {
        fun onDrawerCallback(callback: () -> Unit) {
            drawerCallback = callback
            binding.navDrawer.closeDrawer(GravityCompat.START)
        }
        binding.navView.getHeaderView(0).apply {
            findViewById<ImageView>(R.id.header_icon)?.load(imageStore.getMainImage(ImageStore.APP_ICON))
        }
        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    binding.navDrawer.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.favorite -> {
                    onDrawerCallback {
                        navigateTo(FavoriteActivity::class.java)
                    }
                    true
                }
                R.id.share -> {
                    onDrawerCallback {
                        share(
                            "${getString(R.string.app_share_message)}\n\nhttps://play.google.com/store/apps/details?id=${packageName}",
                            "Share"
                        )
                    }
                    true
                }
                R.id.rate -> {
                    onDrawerCallback {
                        rateApp()
                    }
                    true
                }
                else -> false
            }
        }
    }

    private fun setupNavigationDrawer() {
        binding.navDrawer.addDrawerListener(toggle)
        toggle.setHomeAsUpIndicator(R.drawable.ic_menu)
        toggle.syncState()
        binding.navDrawer.addDrawerListener(
            object : DrawerLayout.DrawerListener {
                override fun onDrawerSlide(drawerView: View, slideOffset: Float) {}

                override fun onDrawerOpened(drawerView: View) {}

                override fun onDrawerClosed(drawerView: View) {
                    drawerCallback?.invoke()
                    drawerCallback = null
                }

                override fun onDrawerStateChanged(newState: Int) {}
            }
        )
    }
}