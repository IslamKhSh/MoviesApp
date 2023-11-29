package com.yassir.movies.ui.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

@Composable
fun NavigationEffects(
    navigationChannel: Channel<NavigationIntent>,
    navHostController: NavHostController
) {
    val activity = (LocalContext.current as? Activity)
    LaunchedEffect(activity, navHostController, navigationChannel) {
        navigationChannel.receiveAsFlow().collect { intent ->
            if (activity?.isFinishing == true) {
                return@collect
            }
            when (intent) {
                is NavigationIntent.NavigateBack -> {
                    if (intent.route != null) {
                        navHostController.popBackStack(intent.route, intent.inclusive)
                    } else {
                        navHostController.popBackStack()
                    }
                }
                is NavigationIntent.NavigateTo -> {
                    navHostController.navigate(intent.route) {
                        anim {
                            enter = intent.navAnimation?.enterAnim ?: -1
                            exit = intent.navAnimation?.exitAnim ?: -1
                        }
                        launchSingleTop = intent.isSingleTop
                        intent.popUpDestination?.let { popUpToDestination ->
                            popUpTo(popUpToDestination.popUpToRoute) {
                                inclusive = intent.popUpDestination.inclusive
                                saveState = intent.popUpDestination.saveState
                            }
                        }
                        restoreState = true
                    }
                }
            }
        }
    }
}
