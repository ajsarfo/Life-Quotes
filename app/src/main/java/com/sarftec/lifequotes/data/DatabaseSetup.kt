package com.sarftec.lifequotes.data

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.booleanPreferencesKey
import com.sarftec.lifequotes.data.json.JsonCategory
import com.sarftec.lifequotes.data.json.JsonQuote
import com.sarftec.lifequotes.data.model.Category
import com.sarftec.lifequotes.data.model.Today
import com.sarftec.lifequotes.presentation.file.editSettings
import com.sarftec.lifequotes.presentation.file.readSettings
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class DatabaseSetup @Inject constructor(
    @ApplicationContext private val context: Context,
    private val database: Database
) {

    suspend fun prepareDatabase() {
        //Insert categories
        context.assets.open("json/Topics.json").use { firstStream ->
            val categories: List<JsonCategory> = Json.decodeFromString(
                firstStream.buffered().readBytes().decodeToString()
            )
            database.categoryDao().insert(
                categories.map { it.toCategory() }
                    .toMutableList()
                    .also {
                        it.add(
                            Category(
                                Category.CATEGORY_INSPIRATION_ID,
                                Category.CATEGORY_INSPIRATION,
                                true
                            )
                        )
                    }
            )

            //Insert Quotes
            val mappedCategories = categories.map {
                it.name to it.id
            }.foldRight(hashMapOf<String, Int>()) { pair, map ->
                map[pair.first] = pair.second
                map
            }

            context.assets.open("json/Quotes.json").use { secondStream ->
                val quotes: List<JsonQuote> = Json.decodeFromString(
                    secondStream.buffered().readBytes().decodeToString()
                )
                val filteredQuotes = quotes.mapNotNull {
                    it.toQuote(mappedCategories)
                }
                database.quoteDao().insert(filteredQuotes)
                database.todayDao().insert(
                    filteredQuotes
                        .filter {
                            it.message.length < 90
                        }.map { quote ->
                            Today(quote.id, quote.message)
                        }
                )
            }
        }
        context.editSettings(APP_CREATED, true)
    }

    suspend fun isDatabaseCreated(): Boolean {
        return context.readSettings(APP_CREATED, false).first()
    }

    companion object {
        val APP_CREATED = booleanPreferencesKey("app_created")
    }
}