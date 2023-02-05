package com.henry.runtime.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.ui.graphics.vector.ImageVector
import com.henry.runtime.R

sealed class BottomBarScreen(
    val route: String,
    @StringRes val resourceId: Int,
    val icon: ImageVector
) {
    object Home : BottomBarScreen("home", R.string.nav_home, Icons.Default.Home)
    object Player : BottomBarScreen("player", R.string.nav_player,  Icons.Default.PlayCircle)
    object History : BottomBarScreen("history", R.string.nav_history, Icons.Default.Timeline)
}
