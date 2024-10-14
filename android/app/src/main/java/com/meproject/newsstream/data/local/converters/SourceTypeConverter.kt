package com.meproject.newsstream.data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.meproject.newsstream.data.local.trending.SourceEntity

class SourceTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromSourceEntity(sourceEntity: SourceEntity?): String? {
        return gson.toJson(sourceEntity)
    }

    @TypeConverter
    fun toSourceEntity(json: String?): SourceEntity? {
        if (json == null) return null
        return gson.fromJson(json, SourceEntity::class.java)
    }
}