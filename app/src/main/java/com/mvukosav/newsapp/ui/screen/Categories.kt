package com.mvukosav.newsapp.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mvukosav.newsapp.R
import com.mvukosav.newsapp.models.TopNewsArticle
import com.mvukosav.newsapp.models.getAllArticleCategories
import com.mvukosav.newsapp.network.NewsManager
import com.mvukosav.newsapp.utils.getTimeAgo
import com.mvukosav.newsapp.utils.stringToDate
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun Categories(onFetchCategory: (String) -> Unit = {}, newsManager: NewsManager) {
    val tabItems = getAllArticleCategories()
    Column {
        LazyRow {
            items(tabItems.size) {
                val category = tabItems[it]
                CategoryTab(
                    category = category.categoryName,
                    onFetchCategory = onFetchCategory,
                    isSelected = newsManager.selectedCategory.value == category
                )
            }
        }
        ArticleContent(articles = newsManager.getArticleByCategory.value.articles ?: listOf())
    }
}

@Composable
fun CategoryTab(category: String, isSelected: Boolean = false, onFetchCategory: (String) -> Unit) {
    val background =
        if (isSelected) colorResource(id = R.color.purple_200) else colorResource(id = R.color.teal_200)
    Surface(
        modifier = Modifier
            .padding(
                horizontal = 4.dp,
                vertical = 16.dp
            )
            .clickable {
                onFetchCategory(category)
            },
        shape = MaterialTheme.shapes.small,
        color = background
    ) {
        Text(
            text = category,
            style = MaterialTheme.typography.body2,
            color = Color.Black,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun ArticleContent(articles: List<TopNewsArticle>, modifier: Modifier = Modifier) {
    LazyColumn {
        items(articles) { article ->
            Card(
                modifier = modifier.padding(8.dp),
                border = BorderStroke(
                    width = 2.dp,
                    color = colorResource(id = R.color.black)
                )
            ) {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    CoilImage(
                        imageModel = article.urlToImage,
                        modifier = Modifier.size(100.dp),
                        contentScale = ContentScale.Crop,
                        error = ImageBitmap.imageResource(id = R.drawable.breaking_news),
                        placeHolder = painterResource(
                            id = R.drawable.breaking_news
                        )
                    )
                    Column(modifier.padding(8.dp)) {
                        Text(
                            text = article.title ?: "Not Available",
                            fontWeight = FontWeight.Bold,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis
                        )
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = modifier.fillMaxWidth()
                        ) {
                            Text(text = article.author ?: "Not Available")
                            Text(text = stringToDate(article.publishedAt!!).getTimeAgo())
                        }
                    }
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoriesPreview() {
    ArticleContent(
        articles = listOf(
            TopNewsArticle(
                author = "Marko Vukosav",
                title = "Legenda o MV",
                description = "Kako je marko postao legenda u gradu Zagrebu sa puno oblaka i dima preko jedne gore zelene",
                publishedAt = "2023-01-01T04:42:40Z"
            )
        )
    )
}
