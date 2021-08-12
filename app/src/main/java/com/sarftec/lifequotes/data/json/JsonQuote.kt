package com.sarftec.lifequotes.data.json

import com.sarftec.lifequotes.data.DatabaseSetup
import com.sarftec.lifequotes.data.model.Category
import com.sarftec.lifequotes.data.model.Quote
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class JsonQuote(
    @SerialName("_id") val id: Int,
    val author: String?,
    @SerialName("quote") val quote: String?,
    val favorite: Int,
    @SerialName("topic")
    val category: String?
) {

    fun toQuote(map: Map<String, Int>) : Quote? {
        return when {
            quote == null -> null
            category == null -> Quote(
                categoryId = Category.CATEGORY_INSPIRATION_ID,
                name = author ?: "UNKNOWN",
                message = quote.replace("\\", "").replace("&#39;", "'")
            )
            else -> {
                map[category]?.let {
                    Quote(
                        categoryId = it,
                        name = author ?: "UNKNOWN",
                        message = quote.replace("\\", "").replace("&#39;", "'")
                    )
                } ?: throw Exception("Can't find id for category : $category")
            }
        }
    }
}