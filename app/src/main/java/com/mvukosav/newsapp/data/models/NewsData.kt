package com.mvukosav.newsapp.data.models

import com.mvukosav.newsapp.R

data class NewsData(
    val newsId: Int,
    val title: String,
    val image: Int = R.drawable.news_,
    val author: String,
    val description: String,
    val publishedAt: String
)
