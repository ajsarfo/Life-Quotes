package com.sarftec.lifequotes.presentation.image

import android.content.Context
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageStore @Inject constructor(
    @ApplicationContext context: Context
) {

    fun getMainImage(name: String) : Uri {
        return name.toAssetUri("top_images")
    }

    private fun String.toAssetUri(folder: String) : Uri {
        return Uri.parse("file:///android_asset/$folder/$this")
    }

    companion object {
        const val MOTIVATION_IMAGE = "motivation.jpg"
        const val TODAY_ICON = "today_icon.jpg"
        const val CATEGORY_IMAGE = "category.jpg"
        const val APP_ICON = "app_icon.png"
    }
}
