package com.mvukosav.newsapp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Source
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarMenuScreen(val route: String, val icon: ImageVector, val title: String) {
    object TopNews : BottomBarMenuScreen(
        route = "top news", icon = Icons.Outlined.Home, title = "Top news"
    )
    object Categories : BottomBarMenuScreen(
        route = "categories", icon = Icons.Outlined.Category, title = "Categories"
    )
    object Sources : BottomBarMenuScreen(
        route = "sources", icon = Icons.Outlined.Source, title = "Sources"
    )
}
