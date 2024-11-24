package com.meproject.newsstream.data.local.explore

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "all_news_item")
data class AllNewsItemEntity(
    @PrimaryKey @ColumnInfo("url") val url: String,
    @ColumnInfo("index") val index: Int,
    @ColumnInfo("category_name") val categoryName: String,
    @ColumnInfo("published_at") val publishedAt: String,
    @ColumnInfo("sentiment") val sentiment: String,
    @ColumnInfo("source") val source: String,
    @ColumnInfo("source_image_url") val sourceImageUrl: String,
    @ColumnInfo("title") val title: String,
    @ColumnInfo("url_to_image") val urlToImage: String
)
