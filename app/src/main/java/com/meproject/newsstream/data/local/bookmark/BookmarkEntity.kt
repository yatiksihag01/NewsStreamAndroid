package com.meproject.newsstream.data.local.bookmark

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.meproject.newsstream.data.local.converter.SourceTypeConverter
import com.meproject.newsstream.data.remote.dto.article.SourceDto

@Keep
@Entity(tableName = "bookmarks")
data class BookmarkEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Int = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "description") val description: String? = null,
    @ColumnInfo(name = "url_to_image") val urlToImage: String? = null,
    @ColumnInfo(name = "published_at") val publishedAt: String,
    @TypeConverters(SourceTypeConverter::class) @ColumnInfo(name = "source") val source: SourceDto,
    @ColumnInfo(name = "sentiment") val sentiment: String,
    @ColumnInfo(name = "category") val category: String? = null
)
