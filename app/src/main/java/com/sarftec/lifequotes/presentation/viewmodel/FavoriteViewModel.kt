package com.sarftec.lifequotes.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.sarftec.lifequotes.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    repository: Repository,
    stateHandle: SavedStateHandle
) : BaseQuoteViewModel(repository, stateHandle) {

    override fun toolbarTitle(): String?  = "Favorite Quotes"

    override fun fetch() {
        viewModelScope.launch {
            _quotes.value = repository.getFavoriteQuotes()
        }
    }
}