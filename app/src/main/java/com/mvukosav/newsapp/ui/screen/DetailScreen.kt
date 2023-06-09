package com.mvukosav.newsapp.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mvukosav.newsapp.R
import com.mvukosav.newsapp.data.models.TopNewsArticle
import com.mvukosav.newsapp.utils.getTimeAgo
import com.mvukosav.newsapp.utils.stringToDate
import com.skydoves.landscapist.coil.CoilImage

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DetailScreen(article: TopNewsArticle, scrollState: ScrollState, navController: NavController) {
    Scaffold(topBar = {
        TopBar(onBackPressed = {
            navController.popBackStack()
        })
    }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CoilImage(
                imageModel = article.urlToImage,
                contentScale = ContentScale.Crop,
                error = ImageBitmap.imageResource(id = R.drawable.breaking_news),
                placeHolder = ImageBitmap.imageResource(id = R.drawable.news_picture)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                InfoWithIcon(icon = Icons.Default.Edit, info = article.author ?: "Not available")
                InfoWithIcon(
                    icon = Icons.Default.DateRange,
                    info = stringToDate(article.publishedAt!!).getTimeAgo()
                )
            }
            Text(
                text = article.title ?: "Not available", fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start)
            )
            Text(
                text = article.description ?: "Not available", modifier = Modifier
                    .padding(vertical = 16.dp)
                    .align(Alignment.Start)
            )
        }
    }
}

@Composable
fun TopBar(onBackPressed: () -> Unit = {}, title: String = "Detail Screen") {
    TopAppBar(title = {
        Text(
            text = title,
            fontWeight = FontWeight.SemiBold
        )
    }, navigationIcon = {
        IconButton(
            onClick = { onBackPressed() }) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
        }
    })
}

@Composable
fun InfoWithIcon(icon: ImageVector, info: String) {
    Row {
        Icon(
            imageVector = icon,
            contentDescription = "",
            modifier = Modifier.padding(end = 8.dp),
            colorResource(id = R.color.purple_500)
        )
        Text(text = info)
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    DetailScreen(
        article = TopNewsArticle(
            author = "Marko Vukosav",
            title = "Legenda o MV",
            description = "Kako je marko postao legenda u gradu Zagrebu sa puno oblaka i dima preko jedne gore zelene",
            publishedAt = "2023-01-01T04:42:40Z"
        ), rememberScrollState(), rememberNavController()
    )
}
