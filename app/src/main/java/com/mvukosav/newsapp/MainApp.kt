package com.mvukosav.newsapp

import android.app.Application
import com.mvukosav.newsapp.data.Repository
import com.mvukosav.newsapp.network.Api
import com.mvukosav.newsapp.network.NewsManager

class MainApp : Application() {
    private val manager by lazy {
        NewsManager(Api.retrofitService)
    }

    val repository by lazy {
        Repository(manager)
    }
}