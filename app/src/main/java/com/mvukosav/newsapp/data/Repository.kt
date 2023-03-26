package com.mvukosav.newsapp.data

import com.mvukosav.newsapp.network.NewsManager

class Repository(val manager: NewsManager) {

    suspend fun getArticles() = manager.getArticles("us")
    suspend fun getArticlesByCategory(category: String) = manager.getArticlesByCategory(category)
    suspend fun getArticlesBySource(source: String) = manager.getArticlesBySource(source)
    suspend fun getArticleBySearch(query: String) = manager.getArticlesByQuery(query)

}