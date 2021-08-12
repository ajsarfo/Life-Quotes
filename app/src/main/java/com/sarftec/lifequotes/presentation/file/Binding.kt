package com.sarftec.lifequotes.presentation.file

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BaseObservable
import androidx.databinding.BindingAdapter
import coil.load
import com.google.android.material.card.MaterialCardView
import com.sarftec.lifequotes.presentation.image.CoilHolder
import com.sarftec.lifequotes.presentation.image.ImageHolder
import kotlin.reflect.KProperty

class Bindable<T : Any>(private var value: T, private val tag: Int) {
    operator fun <U : BaseObservable> getValue(ref: U, property: KProperty<*>): T = value
    operator fun <U : BaseObservable> setValue(ref: U, property: KProperty<*>, newValue: T) {
        value = newValue
        ref.notifyPropertyChanged(tag)
    }
}


fun <T : Any> bindable(value: T, tag: Int): Bindable<T> = Bindable(value, tag)

@BindingAdapter("coilImage")
fun coilImage(imageView: ImageView, holder: CoilHolder) {
    when(holder) {
        is CoilHolder.UriHolder -> imageView.load(holder.uri) {

        }
        else -> {

        }
    }
}

@BindingAdapter("image")
fun changeImage(imageView: ImageView, imageHolder: ImageHolder) {
    imageHolder.let {
        when (it) {
            is ImageHolder.ImageBitmap -> imageView.setImageBitmap(it.image)
            is ImageHolder.ImageDrawable -> imageView.setImageResource(it.icon)
            else -> { }
        }
    }
}

@BindingAdapter("color")
fun changeBackgroundColor(cardView: MaterialCardView, color: Int) {
    cardView.setCardBackgroundColor(color)
}