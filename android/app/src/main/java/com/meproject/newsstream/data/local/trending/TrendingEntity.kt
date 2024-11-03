package com.meproject.newsstream.data.local.trending

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "trending_articles")
data class TrendingEntity(
    @ColumnInfo(name = "index") val index: Int,
    @PrimaryKey @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "published_at") val publishedAt: String,
    @ColumnInfo(name = "source") val source: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "url_to_image") val urlToImage: String?,
    @ColumnInfo(name = "sentiment") val sentiment: String
)
