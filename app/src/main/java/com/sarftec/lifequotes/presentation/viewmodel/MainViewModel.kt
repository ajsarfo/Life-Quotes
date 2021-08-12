package com.sarftec.lifequotes.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarftec.lifequotes.R
import com.sarftec.lifequotes.data.repository.Repository
import com.sarftec.lifequotes.presentation.image.CoilHolder
import com.sarftec.lifequotes.presentation.image.ImageStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class Section(
    val title: String,
    val image: CoilHolder
)

class Today(
    val message: String,
    val icon: CoilHolder
)

class MainSections(
    val first: Section,
    val second: Section,
    val today: Today
)

class PaletteIcons(
    val rate: CoilHolder,
    val share: CoilHolder,
    val moreApps: CoilHolder
)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    private val imageStore: ImageStore
) : ViewModel() {

    private val _mainSections = MutableLiveData<MainSections>()
    val mainSection: LiveData<MainSections>
    get() = _mainSections

    private val _paletteIcons = MutableLiveData<PaletteIcons>()
    val paletteIcons: LiveData<PaletteIcons>
    get() = _paletteIcons

    fun fetch() {
        viewModelScope.launch {
           setMainSections()
            setMainIcons()
        }
    }

    private fun setMainIcons() {
        _paletteIcons.value = PaletteIcons(
            rate = CoilHolder.DrawableHolder(R.drawable.rate),
            share = CoilHolder.DrawableHolder(R.drawable.share),
            moreApps = CoilHolder.DrawableHolder(R.drawable.more_apps)
        )
    }

    private suspend fun setMainSections() {
        val todayQuote = repository.getTodayQuote()
        _mainSections.value = MainSections(
            Section(
                "Motivational\nQuotes",
                CoilHolder.UriHolder(imageStore.getMainImage(ImageStore.MOTIVATION_IMAGE))
            ),
            Section(
                "Category\nQuotes",
                CoilHolder.UriHolder(imageStore.getMainImage(ImageStore.CATEGORY_IMAGE))
            ),
            Today(
                todayQuote.message,
                CoilHolder.UriHolder(imageStore.getMainImage(ImageStore.TODAY_ICON))
            )
        )
    }

    fun getTodayMessage() : String? = _mainSections.value?.today?.message
}