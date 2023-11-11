package com.henry.runtime.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.ui.graphics.vector.ImageVector
import com.henry.runtime.R

sealed class BottomBarScreen(
    val route: String,
    @StringRes val resourceId: Int,
    val icon: ImageVector
) {
    object Home : BottomBarScreen("home", R.string.nav_home, Icons.Default.Home)
    object Profile : BottomBarScreen("profile", R.string.nav_profile, Icons.Default.Person)
    object History : BottomBarScreen("history", R.string.nav_history, Icons.Default.Timeline)
}
