package com.mvukosav.newsapp.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mvukosav.newsapp.components.ErrorUI
import com.mvukosav.newsapp.components.LoadingUI
import com.mvukosav.newsapp.data.models.TopNewsArticle
import com.mvukosav.newsapp.network.NewsManager
import com.mvukosav.newsapp.ui.MainViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
fun Sources(
    viewModel: MainViewModel,
    isLoading: MutableState<Boolean>,
    isError: MutableState<Boolean>
) {

    val items = listOf(
        "TechCrunch" to "techcrunch",
        "TalkSport" to "talksport",
        "Business Insider" to "business-insider",
        "Reuters" to "reuters",
        "Politico" to "politico",
        "TheVerge" to "the-verge"
    )

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "${viewModel.sourceName.collectAsState().value} Source")
            },
                actions = {
                    var menuExpanded by remember { mutableStateOf(false) }
                    IconButton(
                        onClick = { menuExpanded = true }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = null
                        )
                    }
                    MaterialTheme(shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(16.dp))) {
                        DropdownMenu(
                            expanded = menuExpanded,
                            onDismissRequest = { menuExpanded = false }) {
                            items.forEach {
                                DropdownMenuItem(onClick = {
                                    viewModel.sourceName.value = it.second
                                    viewModel.getArticlesBySource()
                                    menuExpanded = false
                                }) {
                                    Text(text = it.first)
                                }
                            }
                        }
                    }

                }
            )
        }
    ) {
        when {
            isLoading.value -> {
                LoadingUI()
            }
            isError.value -> {
                ErrorUI()
            }
            else -> {
                viewModel.getArticlesBySource()
                val articles = viewModel.getArticleBySource.collectAsState().value
                SourceContent(articles = articles.articles ?: listOf())
            }
        }
    }
}

@Composable
fun SourceContent(articles: List<TopNewsArticle>) {
    val uriHandler = LocalUriHandler.current
    LazyColumn {
        items(articles) { article ->
            val annotatedString = buildAnnotatedString {
                pushStringAnnotation(tag = "URL", annotation = article.url ?: "newsapi.org")
                withStyle(
                    style = SpanStyle(
                        color = Color.Black,
                        textDecoration = TextDecoration.Underline
                    )
                ) {
                    append("Read full article here")
                }
            }
            Card(
                backgroundColor = Color.Gray,
                elevation = 6.dp,
                modifier = Modifier.padding(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .height(200.dp)
                        .padding(horizontal = 8.dp),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = article.title ?: "Not available",
                        fontWeight = FontWeight.Bold,
                        style = TextStyle(fontSize = 16.sp),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = article.description ?: "Not available",
                        maxLines = 3,
                        style = TextStyle(fontSize = 14.sp),
                        overflow = TextOverflow.Ellipsis
                    )
                    Card(backgroundColor = Color.Yellow, elevation = 6.dp) {
                        ClickableText(
                            text = annotatedString,
                            style = TextStyle(fontSize = 12.sp),
                            modifier = Modifier.padding(4.dp),
                            onClick = {
                                annotatedString.getStringAnnotations(it, it).firstOrNull()
                                    ?.let { result ->
                                        if (result.tag == "URL") {
                                            uriHandler.openUri(result.item)
                                        }
                                    }
                            }
                        )
                    }
                }
            }
        }
    }
}
