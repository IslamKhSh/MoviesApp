package com.yassir.movies.ui.navigation

sealed class NavigationIntent {
    data class NavigateBack(
        val route: String? = null,
        val inclusive: Boolean = false,
    ) : NavigationIntent()

    data class NavigateTo(
        val route: String,
        val isSingleTop: Boolean = false,
        val popUpDestination: PopUpDestination? = null,
        val navAnimation: NavAnimation? = null
    ) : NavigationIntent()
}
