package com.sarftec.lifequotes.data.dao

import androidx.room.*
import com.sarftec.lifequotes.data.model.QUOTE_TABLE
import com.sarftec.lifequotes.data.model.Quote

@Dao
interface QuoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(quote: Quote)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(quotes: List<Quote>)

    @Query("select * from $QUOTE_TABLE where categoryId = :categoryId")
    suspend fun quotes(categoryId: Int) : List<Quote>

    @Query("select * from $QUOTE_TABLE")
    suspend fun quotes() : List<Quote>

    @Query("select * from $QUOTE_TABLE where isFavorite = 1")
    suspend fun favorites() : List<Quote>

    @Query("update $QUOTE_TABLE set isFavorite = :isFavorite where id = :id")
    suspend fun update(id: Int, isFavorite: Boolean)
}