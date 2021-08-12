package com.sarftec.lifequotes.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

const val CATEGORY_TABLE = "category_table"

@Entity(tableName = CATEGORY_TABLE)
class Category(
    @PrimaryKey val id: Int,
    val name: String,
    val isInspiration: Boolean = false //For marking all inspirational quotes into a single category
) {
    companion object {
        const val CATEGORY_INSPIRATION = "Motivational"
        const val CATEGORY_INSPIRATION_ID = Integer.MAX_VALUE
    }
}