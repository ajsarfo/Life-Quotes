package com.sarftec.lifequotes.presentation.image

import android.net.Uri

sealed class CoilHolder {
    class UriHolder(val uri: Uri) : CoilHolder()
    class DrawableHolder(val id: Int) : CoilHolder()
    object Empty : CoilHolder()
}
