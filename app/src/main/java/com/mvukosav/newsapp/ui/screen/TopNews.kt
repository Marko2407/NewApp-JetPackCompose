package com.mvukosav.newsapp.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mvukosav.newsapp.R
import com.mvukosav.newsapp.components.ErrorUI
import com.mvukosav.newsapp.components.LoadingUI
import com.mvukosav.newsapp.components.SearchBar
import com.mvukosav.newsapp.data.models.TopNewsArticle
import com.mvukosav.newsapp.network.NewsManager
import com.mvukosav.newsapp.ui.MainViewModel
import com.mvukosav.newsapp.utils.getTimeAgo
import com.mvukosav.newsapp.utils.stringToDate
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun TopNews(
    navController: NavController,
    articles: List<TopNewsArticle>,
    query: MutableState<String>,
    viewModel: MainViewModel,
    isLoading : MutableState<Boolean>,
    isError : MutableState<Boolean>
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchBar(query = query, viewModel = viewModel)
        val searchedText = query.value
        val resultList = mutableListOf<TopNewsArticle>()
        if (searchedText != "") {
            resultList.addAll(
                viewModel.getArticleByQuery.collectAsState().value.articles ?: articles
            )
        } else {
            resultList.addAll(articles)
        }

        when {
            isLoading.value ->{
                LoadingUI()
            }
            isError.value ->{
                ErrorUI()
            }
            else -> {
                LazyColumn {
                    items(resultList.size) { index ->
                        TopNewsItem(article = resultList[index], onNewsClick = {
                            navController.navigate("Detail/${index}")
                        })
                    }
                }
            }
        }
    }
}

@Composable
fun TopNewsItem(article: TopNewsArticle, onNewsClick: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .height(200.dp)
            .padding(8.dp)
            .clickable {
                onNewsClick()
            }
    ) {
        CoilImage(
            imageModel = article.urlToImage,
            contentScale = ContentScale.Crop,
            error = ImageBitmap.imageResource(id = R.drawable.breaking_news),
            placeHolder = ImageBitmap.imageResource(id = R.drawable.news_picture),
            modifier = Modifier.fillMaxWidth()
        )
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            article.publishedAt?.let {
                Text(
                    text = stringToDate(article.publishedAt).getTimeAgo(),
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(100.dp))
            article.title?.let {
                Text(
                    text = article.title,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopNewsPreview() {
    TopNewsItem(
        TopNewsArticle(
            author = "Marko Vukosav",
            title = "Legenda o MV",
            description = "Kako je marko postao legenda u gradu Zagrebu sa puno oblaka i dima preko jedne gore zelene",
            publishedAt = "2023-01-01T:04:42:40Z"
        )
    )
}
