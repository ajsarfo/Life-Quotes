package com.sarftec.lifequotes.presentation.viewmodel

import android.os.Bundle
import androidx.lifecycle.*
import com.sarftec.lifequotes.data.model.Quote
import com.sarftec.lifequotes.data.repository.Repository
import kotlinx.coroutines.launch

abstract class BaseQuoteViewModel (
    protected val repository: Repository,
    protected val stateHandle: SavedStateHandle
) : ViewModel() {

    protected val _quotes = MutableLiveData<List<Quote>>()
    val quotes: LiveData<List<Quote>>
        get() = _quotes

    abstract fun fetch()

    abstract fun toolbarTitle() : String?

    fun saveQuoteFavorite(quote: Quote) {
        viewModelScope.launch {
            repository.updateQuoteFavorite(quote)
        }
    }

    fun setBundle(bundle: Bundle) {
        stateHandle.set("bundle", bundle)
    }
}