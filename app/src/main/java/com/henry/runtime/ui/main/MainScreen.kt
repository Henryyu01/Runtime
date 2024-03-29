package com.henry.runtime.ui.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.henry.runtime.profile.ProfileScreen
import com.henry.runtime.ui.history.HistoryScreen
import com.henry.runtime.ui.history.HistoryViewModel
import com.henry.runtime.ui.home.HomeScreen
import com.henry.runtime.ui.home.HomeViewModel
import com.henry.runtime.ui.navigation.BottomBarScreen
import com.henry.runtime.ui.player.PlayerScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onSpotifyAuthentication: () -> Unit,
    historyViewModel: HistoryViewModel,
    homeViewModel: HomeViewModel,
) {
    val navController = rememberNavController()
    Scaffold(
       bottomBar = { BottomBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = BottomBarScreen.Home.route,
            Modifier.padding(innerPadding)
        ) {
            composable("home") {
                HomeScreen(
                    onSpotifyAuthentication = onSpotifyAuthentication,
                    onNavigateToPlayerScreen = { navController.navigate("player") }
                )
            }
            composable("history") { HistoryScreen(historyViewModel) }
            composable("profile") { ProfileScreen() }
            navigation(startDestination = "playerScreen", route = "player") {
                composable("playerScreen") { PlayerScreen() }
            }
        }
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val items = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.History,
        BottomBarScreen.Profile,
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomBarDestination = items.any { it.route == currentDestination?.route }
    if (bottomBarDestination) {
        NavigationBar {
            items.forEach { screen ->
                NavigationBarItem(
                    icon = { Icon(imageVector = screen.icon, contentDescription = "") },
                    selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                    onClick = {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}
