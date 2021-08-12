package com.sarftec.lifequotes.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sarftec.lifequotes.data.model.CATEGORY_TABLE
import com.sarftec.lifequotes.data.model.Category

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(categories: List<Category>)

    @Query("select * from $CATEGORY_TABLE where isInspiration = 0")
    suspend fun categories() : List<Category>
}