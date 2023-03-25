package com.mvukosav.newsapp.models

import  com.mvukosav.newsapp.models.ArticleCategory.*

enum class ArticleCategory(val categoryName: String) {
    BUSINESS("business"),
    ENTERTAINMENT("entertainment"),
    GENERAL("general"),
    HEALTH("health"),
    SCIENCE("science"),
    SPORTS("sports"),
    TECH("tech")
}

fun getAllArticleCategories(): List<ArticleCategory> {
    return listOf(
        ArticleCategory.BUSINESS,
        ArticleCategory.ENTERTAINMENT,
        ArticleCategory.GENERAL,
        ArticleCategory.SPORTS,
        ArticleCategory.TECH,
        ArticleCategory.SCIENCE,
        ArticleCategory.HEALTH
    )
}

fun getArticleCategory(category: String): ArticleCategory? {
    val map = values().associateBy(ArticleCategory::categoryName)
    return map[category]
}