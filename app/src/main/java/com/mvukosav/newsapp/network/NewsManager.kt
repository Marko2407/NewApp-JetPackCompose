package com.mvukosav.newsapp.network

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.mvukosav.newsapp.data.models.ArticleCategory
import com.mvukosav.newsapp.data.models.TopNewsResponse
import com.mvukosav.newsapp.data.models.getArticleCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsManager(private val service: NewsService) {
    private val _newsResponse = mutableStateOf(TopNewsResponse())
    val newResponse: State<TopNewsResponse>
        @Composable get() = remember {
            _newsResponse
        }

    private val _getArticleBySource = mutableStateOf(TopNewsResponse())
    val getArticleBySource: State<TopNewsResponse>
        @Composable get() = remember {
            _getArticleBySource
        }

    private val _getArticleByQuery = mutableStateOf(TopNewsResponse())
    val getArticleByQuery: State<TopNewsResponse>
        @Composable get() = remember {
            _getArticleByQuery
        }

    private val selectedCategory: MutableState<ArticleCategory?> = mutableStateOf(null)
    val sourceName = mutableStateOf("abc-news")
    val query = mutableStateOf("")

    suspend fun getArticles(country: String): TopNewsResponse =
        withContext(Dispatchers.IO) {
            service.getTopArticles(country)
        }

    suspend fun getArticlesByCategory(category: String): TopNewsResponse =
        withContext(Dispatchers.IO) {
            service.getArticlesByCategory(category)
        }

    suspend fun getArticlesBySource(source: String): TopNewsResponse =
        withContext(Dispatchers.IO) {
            service.getArticlesBySource(source)
        }

    suspend fun getArticlesByQuery(query: String) : TopNewsResponse =
        withContext(Dispatchers.IO) {
            service.getArticles(query)
        }

    fun onSelectedCategoryChanged(category: String) {
        val newCategory = getArticleCategory(category = category)
        selectedCategory.value = newCategory
    }
}