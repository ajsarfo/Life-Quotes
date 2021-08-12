package com.sarftec.lifequotes.data.repository

import com.sarftec.lifequotes.data.model.Category
import com.sarftec.lifequotes.data.model.Quote
import com.sarftec.lifequotes.data.model.Today

interface Repository {
    suspend fun getCategories(): List<Category>
    suspend fun getQuotesForCategory(categoryId: Int): List<Quote>
    suspend fun getFavoriteQuotes(): List<Quote>
    suspend fun updateQuoteFavorite(quote: Quote)
    suspend fun getTodayQuote(): Today
}