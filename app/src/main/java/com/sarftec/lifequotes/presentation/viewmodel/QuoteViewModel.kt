package com.sarftec.lifequotes.presentation.viewmodel

import android.os.Bundle
import androidx.lifecycle.*
import com.sarftec.lifequotes.data.model.Category
import com.sarftec.lifequotes.data.repository.Repository
import com.sarftec.lifequotes.data.model.Quote
import com.sarftec.lifequotes.presentation.activity.BaseActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class QuoteViewModel @Inject constructor(
    repository: Repository,
    stateHandle: SavedStateHandle
) : BaseQuoteViewModel(repository, stateHandle) {



    override fun toolbarTitle() : String? {
        return stateHandle.get<Bundle>("bundle")?.getString(BaseActivity.CATEGORY_SELECTED_NAME)?.let {
            "$it Quotes"
        }
    }

    override fun fetch() {
        if(_quotes.value != null) return
        viewModelScope.launch {
            stateHandle.get<Bundle>("bundle")?.let { bundle ->
                val seed = (0 until 100).random()
                stateHandle.set("seed", seed)
                val categoryId = bundle.getInt(BaseActivity.CATEGORY_SELECTED_ID)
                _quotes.value = repository.getQuotesForCategory(categoryId).shuffled(Random(seed))
            }
        }
    }

    fun isInspirationalQuotes() : Boolean {
        return stateHandle.get<Bundle>("bundle")?.let { bundle ->
            bundle.getString(BaseActivity.INSPIRATIONAL_MARKER)?.let {
               it == BaseActivity.IS_INSPIRATIONAL_QUOTES
            } ?: false
        } ?: false
    }

    fun getSeed(): Int {
        return stateHandle.get<Int>("seed") ?: 0
    }
}