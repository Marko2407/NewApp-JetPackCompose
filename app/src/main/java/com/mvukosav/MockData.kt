package com.mvukosav

import com.mvukosav.newsapp.R
import com.mvukosav.newsapp.models.NewsData

object MockData {
    val topNewsList = listOf<NewsData>(
        NewsData(
            newsId = 1,
            image = R.drawable.breaking_news,
            author = "Marko",
            title = "Marko je legenda",
            description = "Kako je Marko postao legenda",
            publishedAt = "2023-11-04T03:21:00Z"
        ),
        NewsData(
            newsId = 2,
            image = R.drawable.news_,
            author = "Ivan",
            title = "Marko je legenda",
            description = "Kako je Marko postao legenda jer je najbolji",
            publishedAt = "2023-11-04T03:43:00Z"
        ),
        NewsData(
            newsId = 3,
            image = R.drawable.news_picture,
            author = "Matej",
            title = "Marko je legenda",
            description = "Kako je Marko postao legenda jer je najbolji",
            publishedAt = "2023-11-04T03:32:00Z"
        ),
        NewsData(
            newsId = 4,
            image = R.drawable.news_picture_2,
            author = "Mateo",
            title = "Marko je legenda",
            description = "Kako je Marko postao legenda jer je najjaci na svijetu",
            publishedAt = "2023-11-04T03:56:00Z"
        ),
        NewsData(
            newsId = 5,
            image = R.drawable.breaking_news,
            author = "Patrik",
            title = "Marko je legenda",
            description = "Kako je Marko postao legenda",
            publishedAt = "2023-11-04T03:57:00Z"
        ),
        NewsData(
            newsId = 6,
            image = R.drawable.news_,
            author = "Antonio",
            title = "Marko je legenda",
            description = "Kako je Marko postao legenda",
            publishedAt = "2023-11-04T03:34:00Z"
        ),
        NewsData(
            newsId = 7,
            image = R.drawable.news_picture,
            author = "Dea",
            title = "Dea nije legenda",
            description = "Kako je Marko postao legenda",
            publishedAt = "2023-11-04T03:23:00Z"
        ),
        NewsData(
            newsId = 8,
            image = R.drawable.breaking_news,
            author = "Karla",
            title = "Karla nije legenda",
            description = "Kako je Marko postao legenda",
            publishedAt = "2023-11-04T03:13:00Z"
        ),
        NewsData(
            newsId = 9,
            image = R.drawable.news_picture_2,
            author = "Mirna",
            title = "Mirna nije legenda",
            description = "Kako je Marko postao legenda",
            publishedAt = "2023-11-04T03:01:00Z"
        ),
        NewsData(
            newsId = 10,
            image = R.drawable.breaking_news,
            author = "Davor",
            title = "Davor je legenda",
            description = "Kako je Marko postao legenda",
            publishedAt = "2023-11-04T03:24:00Z"
        ),
    )

    fun getNews(newsId: Int?): NewsData {
        return topNewsList.first { it.newsId == newsId }
    }
}
