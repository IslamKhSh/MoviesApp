package com.yassir.movies.ui.navigation

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel

object AppNavigatorImpl : AppNavigator {
    override val navigationChannel = Channel<NavigationIntent>(
        capacity = Int.MAX_VALUE,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )

    override suspend fun navigateBack(route: String?, inclusive: Boolean) {
        navigationChannel.send(
            NavigationIntent.NavigateBack(
                route = route,
                inclusive = inclusive
            )
        )
    }

    override fun tryNavigateBack(route: String?, inclusive: Boolean) {
        navigationChannel.trySend(
            NavigationIntent.NavigateBack(
                route = route,
                inclusive = inclusive
            )
        )
    }

    override suspend fun navigateTo(
        route: String,
        isSingleTop: Boolean,
        popUpDestination: PopUpDestination?,
        navAnimation: NavAnimation?
    ) {
        navigationChannel.send(
            NavigationIntent.NavigateTo(
                route = route,
                isSingleTop = isSingleTop,
                popUpDestination = popUpDestination,
                navAnimation = navAnimation
            )
        )
    }

    override fun tryNavigateTo(
        route: String,
        isSingleTop: Boolean,
        popUpDestination: PopUpDestination?,
        navAnimation: NavAnimation?
    ) {
        navigationChannel.trySend(
            NavigationIntent.NavigateTo(
                route = route,
                isSingleTop = isSingleTop,
                popUpDestination = popUpDestination,
                navAnimation = navAnimation
            )
        )
    }
}
