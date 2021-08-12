package com.sarftec.lifequotes.data.repository

import com.sarftec.lifequotes.data.Database
import com.sarftec.lifequotes.data.model.Category
import com.sarftec.lifequotes.data.model.Quote
import com.sarftec.lifequotes.data.model.Today
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val database: Database
): Repository {

    override suspend fun getCategories(): List<Category> {
        return database.categoryDao().categories()
    }

    override suspend fun getQuotesForCategory(categoryId: Int): List<Quote> {
        return if(categoryId == Category.CATEGORY_INSPIRATION_ID) {
            database.quoteDao().quotes()
        }
        else database.quoteDao().quotes(categoryId)
    }

    override suspend fun getFavoriteQuotes(): List<Quote> {
        return database.quoteDao().favorites()
    }

    override suspend fun updateQuoteFavorite(quote: Quote) {
        database.quoteDao().update(quote.id, quote.isFavorite)
    }

    override suspend fun getTodayQuote(): Today {
       return database.todayDao().random()
    }
}