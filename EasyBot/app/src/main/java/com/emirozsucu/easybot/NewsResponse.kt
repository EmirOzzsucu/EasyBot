package com.emirozsucu.easybot.model

// NewsAPI yanıt modeli - haberlerin listesini temsil eder
data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)

// Tekil bir haberi temsil eder
data class Article(
    val title: String,
    val description: String?,
    val url: String
)
