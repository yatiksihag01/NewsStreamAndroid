package com.meproject.newsstream.data.local.bookmark

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "bookmarks")
data class BookmarkEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Int = 0,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "url_to_image") val urlToImage: String,
    @ColumnInfo(name = "source_image_url") val sourceImageUrl: String,
    @ColumnInfo(name = "source_name") val source: String,
    @ColumnInfo(name = "published_at") val publishedAt: String,
)
