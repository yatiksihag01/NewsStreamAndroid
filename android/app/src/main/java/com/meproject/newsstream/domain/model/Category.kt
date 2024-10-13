package com.meproject.newsstream.domain.model

data class Category(
    val id: String,
    val name: String,
    var isSelected: Boolean = false
)