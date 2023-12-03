package com.yassir.movies.ui.screens.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.yassir.movies.ui.navigation.AppNavigator
import com.yassir.movies.ui.navigation.AppNavigatorImpl
import com.yassir.movies.ui.navigation.NavigationEffects
import com.yassir.movies.ui.navigation.NavigationGraph
import com.yassir.movies.ui.theme.MoviesTheme

val LocalAppNavigator = staticCompositionLocalOf<AppNavigator> { AppNavigatorImpl }
val LocalNavController =
    staticCompositionLocalOf<NavHostController> { error("No LocalNavController provided") }

@Composable
fun MainScreenView() {
    val navController = rememberNavController()
    val appNavigator = LocalAppNavigator.current

    NavigationEffects(
        navigationChannel = appNavigator.navigationChannel,
        navHostController = navController
    )

    MoviesTheme {
        CompositionLocalProvider(
            LocalAppNavigator provides AppNavigatorImpl,
            LocalNavController provides navController
        ) {
            NavigationGraph(navController = navController)
        }
    }
}
