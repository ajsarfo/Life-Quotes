package com.sarftec.lifequotes.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarftec.lifequotes.data.repository.Repository
import com.sarftec.lifequotes.data.model.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>>
    get() = _categories

    fun fetch() {
        if(_categories.value != null) return
        viewModelScope.launch {
            _categories.value = repository.getCategories()
        }
    }
}