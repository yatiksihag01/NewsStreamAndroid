package com.meproject.newsstream.data.local.trending

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.meproject.newsstream.data.local.converter.SourceTypeConverter
import com.meproject.newsstream.data.remote.dto.article.SourceDto

@Keep
@Entity(tableName = "trending_articles")
data class TrendingEntity(
    @ColumnInfo(name = "index") val index: Int,

    @ColumnInfo(name = "title") val title: String,

    @PrimaryKey @ColumnInfo(name = "url") val url: String,

    @ColumnInfo(name = "description") val description: String?,

    @ColumnInfo(name = "url_to_image") val urlToImage: String?,

    @ColumnInfo(name = "published_at") val publishedAt: String,

    @TypeConverters(SourceTypeConverter::class) @ColumnInfo(name = "source") val source: SourceDto,

    @ColumnInfo(name = "sentiment") val sentiment: String
)
