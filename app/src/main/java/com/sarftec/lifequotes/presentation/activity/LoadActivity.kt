package com.sarftec.lifequotes.presentation.activity

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.lifecycleScope
import com.sarftec.lifequotes.R
import com.sarftec.lifequotes.data.DatabaseSetup
import com.sarftec.lifequotes.databinding.LayoutLoadingDialogBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import javax.inject.Inject

@AndroidEntryPoint
class LoadActivity : BaseActivity() {

    @Inject
    lateinit var databaseSetup: DatabaseSetup

    private val dialogBinding by lazy {
        LayoutLoadingDialogBinding.inflate(
            LayoutInflater.from(this)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showLoadingDialog()
        lifecycleScope.launchWhenCreated {
            databaseSetup.prepareDatabase()
            delay(1000)
            navigateTo(SplashActivity::class.java, true, R.anim.no_anim, R.anim.no_anim)
        }
    }

    private fun showLoadingDialog() {
        object : AlertDialog(this) {}.apply {
            setView(dialogBinding.root)
            show()
        }
    }

    override fun onBackPressed() {
        finish()
    }
}