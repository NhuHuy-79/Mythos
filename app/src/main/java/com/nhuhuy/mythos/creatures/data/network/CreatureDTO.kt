package com.nhuhuy.mythos.creatures.data.network

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.annotations.JsonAdapter
import java.lang.reflect.Type

data class CreatureDTO(
    val author: String,
    val canon: String,
    val category: String,
    val id: Int,
    val img: List<String>,
    val name: String,
    @JsonAdapter(NicksAdapter::class)
    val nicks: List<String>?,
    val overview: String,
    val wikiUrl: String
)

class NicksAdapter : JsonDeserializer<List<String>> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): List<String> {
        return when {
            json?.isJsonArray == true -> json.asJsonArray.map { it.toString() }
            json?.isJsonPrimitive == true && json.asJsonPrimitive.isString -> listOf(json.asString)
            else -> emptyList()
        }
    }

}

