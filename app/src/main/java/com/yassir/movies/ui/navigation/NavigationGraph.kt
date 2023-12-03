package com.yassir.movies.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.yassir.movies.ui.screens.movieDetails.MovieDetailsScreen
import com.yassir.movies.ui.screens.movies.MoviesListScreen
import com.yassir.movies.utils.MOVIE_ID_PARAM

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

        composable(
            Destination.MovieDetails,
            arguments = listOf(navArgument(name = MOVIE_ID_PARAM) { type = NavType.IntType })
        ) {
            MovieDetailsScreen()
        }
    }
}
