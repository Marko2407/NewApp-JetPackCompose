package com.mvukosav.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mvukosav.newsapp.ui.MainViewModel
import com.mvukosav.newsapp.ui.NewsApp
import com.mvukosav.newsapp.ui.theme.NewsAppTheme

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getTopArticles()
        setContent {
            NewsAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) { NewsApp(viewModel = viewModel) }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NewsAppTheme {
        NewsApp(viewModel = viewModel())
    }
}
