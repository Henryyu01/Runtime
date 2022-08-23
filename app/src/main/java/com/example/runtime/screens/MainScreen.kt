package com.example.runtime.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.runtime.navigation.BottomBarScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
       bottomBar = { BottomBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = BottomBarScreen.Home.route,
            Modifier.padding(innerPadding)
        ) {
            composable("home") { HomeScreen() }
            composable("history") { HistoryScreen() }
            composable("player") { PlayerScreen() }
        }
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val items = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.History,
        BottomBarScreen.Player,
    )

    BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        items.forEach { screen ->

            BottomNavigationItem(
                icon = { Icon(imageVector = screen.icon, contentDescription = "test string") },
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
