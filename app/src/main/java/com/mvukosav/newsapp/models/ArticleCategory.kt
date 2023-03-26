package com.mvukosav.newsapp.models

import  com.mvukosav.newsapp.models.ArticleCategory.*

enum class ArticleCategory(val categoryName: String) {
    BUSINESS("business"),
    ENTERTAINMENT("entertainment"),
    GENERAL("general"),
    HEALTH("health"),
    SCIENCE("science"),
    SPORTS("sports"),
    TECH("tech");
}

fun getAllArticleCategories(): List<ArticleCategory> {
    return listOf(
        BUSINESS,
        ENTERTAINMENT,
        GENERAL,
        SPORTS,
        TECH,
        SCIENCE,
        HEALTH
    )
}

fun getArticleCategory(category: String): ArticleCategory? {
    val map = values().associateBy(ArticleCategory::categoryName)
    return map[category]
}