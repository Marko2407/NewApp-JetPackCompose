package com.mvukosav.newsapp.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
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
import com.mvukosav.newsapp.models.TopNewsArticle
import com.mvukosav.newsapp.network.NewsManager
import com.mvukosav.newsapp.ui.screen.Categories
import com.mvukosav.newsapp.ui.screen.DetailScreen
import com.mvukosav.newsapp.ui.screen.Sources
import com.mvukosav.newsapp.ui.screen.TopNews

@Composable
fun NewsApp() {
    val navController = rememberNavController()
    val scrollState = rememberScrollState()
    MainScreen(navController = navController, scrollState = scrollState)
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(navController: NavHostController, scrollState: ScrollState) {
    Scaffold(bottomBar = { BottomMenu(navController = navController) }) {
        Navigation(navController, scrollState, paddingValues = it)
    }
}

@Composable
fun Navigation(
    navController: NavHostController,
    scrollState: ScrollState,
    newsManager: NewsManager = NewsManager(),
    paddingValues: PaddingValues
) {
    val articles = newsManager.newResponse.value.articles
    Log.d("news", articles.toString())

    articles?.let {
        NavHost(
            navController = navController,
            startDestination = BottomBarMenuScreen.TopNews.route,
            modifier = Modifier.padding(paddingValues = paddingValues)
        ) {
            bottomNavigation(navController, articles)
            composable(
                "Detail/{index}",
                arguments = listOf(navArgument("index") { type = NavType.IntType })
            ) { navBackStackEntry ->
                // passing arguments through screens
                val index = navBackStackEntry.arguments?.getInt("index")
                index?.let {
                    val article = articles[index]
                    DetailScreen(article, scrollState = scrollState, navController)
                }

            }
        }
    }


}

fun NavGraphBuilder.bottomNavigation(
    navController: NavHostController,
    articles: List<TopNewsArticle>
) {
    composable(BottomBarMenuScreen.TopNews.route) {
        TopNews(
            navController = navController,
            articles
        )
    }
    composable(BottomBarMenuScreen.Categories.route) { Categories() }
    composable(BottomBarMenuScreen.Sources.route) { Sources() }
}
