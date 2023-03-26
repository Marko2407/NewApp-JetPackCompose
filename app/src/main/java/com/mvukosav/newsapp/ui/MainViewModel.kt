package com.mvukosav.newsapp.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mvukosav.newsapp.MainApp
import com.mvukosav.newsapp.data.models.ArticleCategory
import com.mvukosav.newsapp.data.models.TopNewsResponse
import com.mvukosav.newsapp.data.models.getArticleCategory
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = getApplication<MainApp>().repository

    private val _newsResponse = MutableStateFlow(TopNewsResponse())
    val newsResponse: StateFlow<TopNewsResponse>
        get() = _newsResponse

    private val _getArticleByCategory = MutableStateFlow(TopNewsResponse())
    val getArticleByCategory: StateFlow<TopNewsResponse>
        get() = _getArticleByCategory

    val sourceName = MutableStateFlow("engadget")
    private val _getArticleBySource = MutableStateFlow(TopNewsResponse())
    val getArticleBySource: StateFlow<TopNewsResponse>
        get() = _getArticleBySource

    val query = MutableStateFlow("")
    private val _getArticleByQuery = MutableStateFlow(TopNewsResponse())
    val getArticleByQuery: StateFlow<TopNewsResponse>
        get() = _getArticleByQuery

    private val _selectedCategory: MutableStateFlow<ArticleCategory?> = MutableStateFlow(null)
    val selectedCategory: StateFlow<ArticleCategory?>
        get() = _selectedCategory

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isError = MutableStateFlow(false)
    val isError: StateFlow<Boolean>
        get() = _isError

    private val errorHandler = CoroutineExceptionHandler{
        _, error ->
        if (error is Exception){
            _isError.value = true
        }
    }

    fun getTopArticles() {
        onFetching {
            viewModelScope.launch(errorHandler) {
                _newsResponse.value = repository.getArticles()
                _isLoading.value = false
            }
        }
    }

    fun getArticlesByCategory(category: String) {
        onFetching {
            viewModelScope.launch(errorHandler) {
                _getArticleByCategory.value = repository.getArticlesByCategory(category)
                _isLoading.value = false
            }
        }
    }

    fun getArticlesBySource() {
        onFetching {
            viewModelScope.launch(errorHandler) {
                _getArticleBySource.value = repository.getArticlesBySource(sourceName.value)
                _isLoading.value = false
            }
        }
    }

    fun getArticlesBySearch(query: String) {
        onFetching {
            viewModelScope.launch(errorHandler) {
                _getArticleByQuery.value = repository.getArticleBySearch(query)
                _isLoading.value = false
            }
        }
    }

    fun onSelectedCategoryChanged(category: String) {
        val newCategory = getArticleCategory(category)
        _selectedCategory.value = newCategory
    }

    private fun onFetching(block: () -> Unit = {}) {
        _isLoading.value = true
        block()
    }
}
