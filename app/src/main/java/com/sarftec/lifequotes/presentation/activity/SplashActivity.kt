package com.sarftec.lifequotes.presentation.activity

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.lifecycleScope
import com.sarftec.lifequotes.databinding.ActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class SplashActivity : BaseActivity() {

    private val binding by lazy {
        ActivitySplashBinding.inflate(LayoutInflater.from(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val item = SplashManager(this).getItem()
        binding.splashMainText.apply {
            setTextColor(item.textColor)
            text = item.title
            item.typeface?.let {
                typeface = it
            }
        }
        binding.splashBottomText.apply {
            setTextColor(item.textColor)
            text = item.subtitle
            item.typeface?.let {
                typeface = it
            }
        }
        binding.splashImageLayout.setBackgroundColor(item.backgroundColor)
        statusColor(item.backgroundColor)
        lifecycleScope.launchWhenCreated {
            delay(3500L)
            navigateTo(MainActivity::class.java, true)

        }
    }

    override fun onBackPressed() {
        finish()
    }

    private class SplashManager(private val context: Context) {

        val fonts = arrayOf("bebas.otf", "comfortaa.ttf", "dephion.otf", "limerock.ttf")

        val message = arrayOf(
            "Old age is just a record of one's whole life." to "Age Quote",
            "For success, attitude is equally as important as ability. " to "Attitude Quote",
            "Success is never final, failure is never fatal. It's courage that counts." to "Failure Quote",
            "It takes discipline not to let social media steal your time." to "Time Quote",
            "He who does not trust enough, will not be trusted." to "Trust Quote",
            "Work hard, stay positive, and get upd early, it's the best part of the day." to "Work Quote",
            "If you want peace, you don't talk to your friends. You talk to your enemies" to "Peace Quote",
            "Blessed are those who give without remembering and take without forgetting." to "Wisdom Quote",
            "The successful man will profit from his mistakes and try again in a different way." to "Success Quote",
            "If you want something said, as a man; if you want something done, as a woman." to "Women Quote",
            "Patience and diligence, like faith, remove mountains." to "Patience Quote"
        )

        class Item(
            val typeface: Typeface?,
            val title: String,
            val subtitle: String,
            val textColor: Int,
            val backgroundColor: Int
        )

        fun getItem(): Item {
            val split = f23727b.random().split("@")
            val typeface = Typeface.createFromAsset(context.assets, "fonts/" + fonts.random())
            val pair = message.random()
            return Item(
                typeface = typeface,
                title = "“${pair.first}”",
                subtitle = "-${pair.second}-",
                textColor = m19660d(split[1]),
                backgroundColor = m19660d(split[0])
            )
        }

        /**********************************************/
        /* renamed from: a */

        /*
         var f23726a = intArrayOf(
             R.color.color11,
             R.color.color12,
             R.color.color13,
             R.color.color14,
             R.color.color15,
             R.color.color16,
             R.color.color17,
             R.color.color18,
             R.color.color27
         )
         */


        /* renamed from: b */
        var f23727b = arrayOf(
            "#fdcd00@#26231c",
            "#1c1b21@#ffffff",
            "#3D155F@#DF678C",
            "#4831D4@#CCF381",
            "#317773@#E2D1F9",
            "#121c37@#ffa937",
            "#79bbca@#39324b",
            "#ffadb1@#202f34",
            "#373a3c@#e3b94d",
            "#e38285@#fbfdea",
            "#eebb2c@#6c2c4e",
            "#170e35@#94daef"
        )

        /* renamed from: c */
        fun m19659c(str: String): String {
            return if (str.contains("#")) str else "#$str"
        }

        /* renamed from: d */
        fun m19660d(str: String): Int {
            return Color.parseColor(m19659c(str))
        }
    }
}