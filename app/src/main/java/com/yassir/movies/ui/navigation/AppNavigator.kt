package com.yassir.movies.ui.navigation

import androidx.annotation.AnimRes
import kotlinx.coroutines.channels.Channel

interface AppNavigator {
    val navigationChannel: Channel<NavigationIntent>

    suspend fun navigateBack(route: String? = null, inclusive: Boolean = false,)

    fun tryNavigateBack(route: String? = null, inclusive: Boolean = false,)

    suspend fun navigateTo(
        route: String,
        isSingleTop: Boolean = false,
        popUpDestination: PopUpDestination? = null,
        navAnimation: NavAnimation? = null
    )

    fun tryNavigateTo(
        route: String,
        isSingleTop: Boolean = false,
        popUpDestination: PopUpDestination? = null,
        navAnimation: NavAnimation? = null
    )
}

data class PopUpDestination(
    val popUpToRoute: String,
    val inclusive: Boolean = false,
    val saveState: Boolean = false
)

data class NavAnimation(@AnimRes val enterAnim: Int = -1, @AnimRes val exitAnim: Int = -1)
