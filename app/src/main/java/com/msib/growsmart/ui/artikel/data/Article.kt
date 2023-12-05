package com.msib.growsmart.ui.artikel.data

data class Article(
    val id: Int,
    val timestamp: String,
    val title: String?,
    val name: String?,
    val image: String?,
    val description: String?,
    val article: String?,
    val createdAt: String,
    val updatedAt: String
)

data class ArticlesResponse(
    val error: Boolean,
    val articles: List<Article>
)