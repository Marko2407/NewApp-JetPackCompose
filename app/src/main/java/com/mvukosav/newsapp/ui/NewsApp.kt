package com.mvukosav.newsapp.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mvukosav.MockData
import com.mvukosav.newsapp.BottomBarMenuScreen
import com.mvukosav.newsapp.components.BottomMenu
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
        Navigation(navController, scrollState)
    }
}

@Composable
fun Navigation(navController: NavHostController, scrollState: ScrollState) {
    NavHost(navController = navController, startDestination = "TopNews") {
        bottomNavigation(navController)
        composable("TopNews") {
            TopNews(navController = navController)
        }
        composable(
            "Detail/{newsId}",
            arguments = listOf(navArgument("newsId") { type = NavType.IntType })
        ) { navBackStackEntry ->
            // passing arguments through screens
            val id = navBackStackEntry.arguments?.getInt("newsId")
            DetailScreen(MockData.getNews(id), scrollState = scrollState, navController)
        }
    }

}

fun NavGraphBuilder.bottomNavigation(navController: NavHostController) {
    composable(BottomBarMenuScreen.TopNews.route) { TopNews(navController = navController) }
    composable(BottomBarMenuScreen.Categories.route) { Categories() }
    composable(BottomBarMenuScreen.Sources.route) { Sources() }
}
