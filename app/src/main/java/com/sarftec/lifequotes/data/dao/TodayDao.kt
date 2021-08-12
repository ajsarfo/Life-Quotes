package com.sarftec.lifequotes.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sarftec.lifequotes.data.model.TODAY_TABLE
import com.sarftec.lifequotes.data.model.Today

@Dao
interface TodayDao {

    @Query("select * from $TODAY_TABLE order by random() limit 1")
    suspend fun random() : Today

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(items: List<Today>)
}