package com.yassir.movies.ui.screens.base

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.yassir.movies.ui.navigation.AppNavHost
import com.yassir.movies.ui.navigation.Destination
import com.yassir.movies.ui.navigation.composable
import com.yassir.movies.ui.screens.movies.MoviesListScreen

@Composable
fun NavigationGraph(modifier: Modifier = Modifier, navController: NavHostController,) {
    AppNavHost(
        navController = navController,
        startDestination = Destination.MoviesList,
        modifier = modifier
    ) {
        composable(Destination.MoviesList) {
            MoviesListScreen()
        }
    }
}
