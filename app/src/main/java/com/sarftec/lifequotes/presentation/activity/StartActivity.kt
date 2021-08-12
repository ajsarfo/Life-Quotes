package com.sarftec.lifequotes.presentation.activity

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.sarftec.lifequotes.R
import com.sarftec.lifequotes.data.DatabaseSetup
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class StartActivity : BaseActivity() {

    @Inject
    lateinit var databaseSetup: DatabaseSetup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenCreated {
            val kclass = if(!databaseSetup.isDatabaseCreated()) LoadActivity::class.java
            else SplashActivity::class.java
            navigateTo(kclass, true, R.anim.no_anim, R.anim.no_anim)
        }
    }
}