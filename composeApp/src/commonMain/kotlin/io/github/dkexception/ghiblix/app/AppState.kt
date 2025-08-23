package io.github.dkexception.ghiblix.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.github.dkexception.ghiblix.navigation.TopLevelDestination
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController(),
    startDestination: TopLevelDestination = TopLevelDestination.Home
) = remember(
    navController,
    startDestination
) {
    AppState(
        navController = navController,
        startDestination = startDestination
    )
}

//@Stable
class AppState(
    val navController: NavHostController,
    val startDestination: TopLevelDestination
) {

    val topLevelDestinations: PersistentList<TopLevelDestination> = persistentListOf(
        TopLevelDestination.Home,
        TopLevelDestination.Search,
        TopLevelDestination.Watchlist
    )

    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    var currentTopLevelDestination by mutableStateOf(startDestination)
        private set

    init {
//        scope.launch {
//            navController.currentBackStackEntryFlow
//                .mapNotNull { it.destination }
//                .mapNotNull { destination ->
//                    topLevelDestinations.fastFirstOrNull {
//                        it.route in destination.hierarchy.map(
//                            NavDestination::route
//                        )
//                    }
//                }
//                .collect { topLevelDestination ->
//                    currentTopLevelDestination = topLevelDestination
//                }
//        }

//        scope.launch {
//            snackbarManager.messages.collect { messages ->
//                val message = messages.firstOrNull() ?: return@collect
//                val result = scaffoldState.snackbarHostState.showSnackbar(
//                    message = resources.getString(message.textId, *message.textArgs.toTypedArray()),
//                    actionLabel = message.actionLabelId?.let(resources::getString),
//                    duration = message.duration
//                )
//
//                when (result) {
//                    SnackbarResult.ActionPerformed -> message.onAction?.invoke()
//                    SnackbarResult.Dismissed -> {}
//                }
//
//                snackbarManager.onMessageShown(message.id)
//            }
//        }
    }

    //    fun isTopLevelDestination(destination: NavDestination) = topLevelDestinations.fastAny {
//        it.destination == destination.route
//    }
//
    fun navigateToTopLevelDestination(destination: TopLevelDestination) {

        val currentDestination = navController.currentDestination ?: return

        if (destination == currentDestination) {
            when (destination) {
                TopLevelDestination.Home -> Unit
                TopLevelDestination.Search -> Unit
                TopLevelDestination.Watchlist -> Unit
            }
        } else if (currentDestination.hierarchy.any { it == destination }) {
            navController.popBackStack(
                route = destination::class,
                inclusive = false
            )
        } else {
            navController.navigate(destination) {
                popUpTo(startDestination::class) {
                    saveState = true
                }

                launchSingleTop = true
                restoreState = true
            }
        }
    }
}
