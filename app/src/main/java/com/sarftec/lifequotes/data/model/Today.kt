package com.sarftec.lifequotes.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

const val TODAY_TABLE = "today_table"

@Entity(tableName = TODAY_TABLE)
class Today(
   @PrimaryKey(autoGenerate = true) val id: Int = 0,
   val message: String
)