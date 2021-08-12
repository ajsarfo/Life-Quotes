package com.sarftec.lifequotes.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sarftec.lifequotes.data.dao.CategoryDao
import com.sarftec.lifequotes.data.dao.QuoteDao
import com.sarftec.lifequotes.data.dao.TodayDao
import com.sarftec.lifequotes.data.model.Category
import com.sarftec.lifequotes.data.model.Quote
import com.sarftec.lifequotes.data.model.Today

@Database(entities = [Quote::class, Category::class, Today::class], version = 1, exportSchema = false)
abstract class Database : RoomDatabase() {
    abstract fun categoryDao() : CategoryDao
    abstract fun quoteDao() : QuoteDao
    abstract fun todayDao() : TodayDao
}