package com.mvukosav.newsapp.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mvukosav.newsapp.BottomBarMenuScreen
import com.mvukosav.newsapp.components.BottomMenu
import com.mvukosav.newsapp.data.models.TopNewsArticle
import com.mvukosav.newsapp.network.Api
import com.mvukosav.newsapp.network.NewsManager
import com.mvukosav.newsapp.ui.screen.Categories
import com.mvukosav.newsapp.ui.screen.DetailScreen
import com.mvukosav.newsapp.ui.screen.Sources
import com.mvukosav.newsapp.ui.screen.TopNews

@Composable
fun NewsApp(viewModel: MainViewModel) {
    val navController = rememberNavController()
    val scrollState = rememberScrollState()
    MainScreen(navController = navController, scrollState = scrollState, mainViewModel = viewModel)
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(
    navController: NavHostController,
    scrollState: ScrollState,
    mainViewModel: MainViewModel
) {
    Scaffold(bottomBar = { BottomMenu(navController = navController) }) {
        Navigation(navController, scrollState, paddingValues = it, viewModel = mainViewModel)
    }
}

@Composable
fun Navigation(
    navController: NavHostController,
    scrollState: ScrollState,
    newsManager: NewsManager = NewsManager(Api.retrofitService),
    paddingValues: PaddingValues,
    viewModel: MainViewModel
) {
    val loading by viewModel.isLoading.collectAsState()
    val error by viewModel.isError.collectAsState()
    val articles = mutableListOf<TopNewsArticle>()
    val topArticles = viewModel.newsResponse.collectAsState().value.articles
    articles.addAll(topArticles ?: listOf())
    Log.d("news", articles.toString())

    NavHost(
        navController = navController,
        startDestination = BottomBarMenuScreen.TopNews.route,
        modifier = Modifier.padding(paddingValues = paddingValues)
    ) {
        val queryState = mutableStateOf(viewModel.query.value)
        val isLoading = mutableStateOf(loading)
        val isError = mutableStateOf(error)
        bottomNavigation(navController, articles, queryState, viewModel, isLoading, isError)
        composable(
            "Detail/{index}",
            arguments = listOf(navArgument("index") { type = NavType.IntType })
        ) { navBackStackEntry ->
            // passing arguments through screens
            val index = navBackStackEntry.arguments?.getInt("index")
            index?.let {
                if (queryState.value.isNotEmpty()) {
                    articles.clear()
                    articles.addAll(
                        viewModel.getArticleByQuery.collectAsState().value.articles ?: listOf()
                    )
                } else {
                    articles.clear()
                    articles.addAll(
                        viewModel.newsResponse.collectAsState().value.articles ?: listOf()
                    )
                }
                val article = articles[index]
                DetailScreen(article, scrollState = scrollState, navController)
            }

        }
    }


}

fun NavGraphBuilder.bottomNavigation(
    navController: NavHostController,
    articles: List<TopNewsArticle>,
    query: MutableState<String>,
    viewModel: MainViewModel,
    isLoading: MutableState<Boolean>,
    isError: MutableState<Boolean>
) {
    composable(BottomBarMenuScreen.TopNews.route) {
        TopNews(
            navController = navController,
            articles = articles,
            query = query,
            viewModel = viewModel,
            isLoading = isLoading,
            isError = isError
        )
    }
    composable(BottomBarMenuScreen.Categories.route) {
        viewModel.getArticlesByCategory("business")
        viewModel.onSelectedCategoryChanged("business")

        Categories(
            viewModel = viewModel,
            onFetchCategory = { categoryName ->
                viewModel.onSelectedCategoryChanged(categoryName)
                viewModel.getArticlesByCategory(categoryName)
            },
            isLoading = isLoading,
            isError = isError
        )
    }
    composable(BottomBarMenuScreen.Sources.route) {
        Sources(
            viewModel = viewModel,
            isLoading = isLoading,
            isError = isError
        )
    }
}
