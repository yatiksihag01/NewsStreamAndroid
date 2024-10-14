package com.meproject.newsstream.data.local.trending

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.meproject.newsstream.data.local.converters.SourceTypeConverter

@Keep
@Entity(tableName = "trending_articles")
data class TrendingEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 1,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "published_at") val publishedAt: String,
    @TypeConverters(SourceTypeConverter::class)
    @ColumnInfo(name = "source") val sourceEntity: SourceEntity,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "url_to_image") val urlToImage: String,
    @ColumnInfo(name = "sentiment") val sentiment: String
)
