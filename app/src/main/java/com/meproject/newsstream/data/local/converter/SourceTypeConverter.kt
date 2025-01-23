package com.meproject.newsstream.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.meproject.newsstream.data.remote.dto.article.SourceDto

class SourceTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromSourceDto(sourceDto: SourceDto): String? {
        return gson.toJson(sourceDto)
    }

    @TypeConverter
    fun toSourceDto(json: String?): SourceDto? {
        if (json == null) return null
        return gson.fromJson(json, SourceDto::class.java)
    }
}