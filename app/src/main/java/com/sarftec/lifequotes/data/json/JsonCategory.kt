package com.sarftec.lifequotes.data.json

import com.sarftec.lifequotes.data.model.Category
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class JsonCategory(
    @SerialName("_id") val id: Int,
    @SerialName("topic") val name: String
){
    fun toCategory() : Category = Category(id, name)
}