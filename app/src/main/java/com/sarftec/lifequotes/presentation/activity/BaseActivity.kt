package com.sarftec.lifequotes.presentation.activity

import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.sarftec.lifequotes.R
import com.sarftec.lifequotes.Dependency
import com.sarftec.lifequotes.presentation.manager.NetworkManager
import com.sarftec.lifequotes.presentation.image.ImageStore
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity() {

    //These are been used by dependency

    @Inject
    lateinit var imageStore : ImageStore

    @Inject
    lateinit var networkManager: NetworkManager
    /////////////////////////////////////

    protected val dependency by lazy {
        Dependency(this,  imageStore)
    }

    protected fun <T> navigateTo(
        klass: Class<T>,
        finish: Boolean = false,
        slideIn: Int = R.anim.slide_in_right,
        slideOut: Int = R.anim.slide_out_left,
        bundle: Bundle? = null
    ) {
        val intent = Intent(this, klass).also {
            it.putExtra(ACTIVITY_BUNDLE, bundle)
        }
        startActivity(intent)
        if (finish) finish()
        overridePendingTransition(slideIn, slideOut)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    protected fun setStatusBarBackgroundLight() {
        fun dayMode() {
            when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> {
                    window.decorView.windowInsetsController?.setSystemBarsAppearance(
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                    )
                }
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                    val view = window.decorView
                    view.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                }
            }
        }
        fun darkMode() {
            when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> {
                    window.decorView.windowInsetsController?.setSystemBarsAppearance(
                        0,
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                    )
                }
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                    val view = window.decorView
                    view.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
                }
            }
        }
        when(resources.configuration.uiMode and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> {  }
            Configuration.UI_MODE_NIGHT_NO -> { dayMode() }
            Configuration.UI_MODE_NIGHT_UNDEFINED -> {  }
        }
    }


    fun statusColor(color: Int) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.apply {
                addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                statusBarColor = color
            }
        }
    }
    companion object {
        const val ACTIVITY_BUNDLE = "activity_bundle"
        const val CATEGORY_SELECTED_NAME = "category_selected_name"
        const val CATEGORY_SELECTED_ID = "category_selected_id"
        const val QUOTE_SELECTED_ID = "quote_selected_id"
        const val NAVIGATION_ROOT = "navigation_root"
        const val NAVIGATION_QUOTE_LIST = "quote_list"
        const val NAVIGATION_MAIN = "navigation_main"
        const val RANDOM_SEED = "random_seed"
        const val INSPIRATIONAL_MARKER = "inspirational_marker"
        const val IS_INSPIRATIONAL_QUOTES = "is_inspirational_quotes"
        const val NAVIGATION_FAVORITE_LIST = "favorite_list"

        val modifiedQuoteList = hashMapOf<Int, Boolean>()
    }
}