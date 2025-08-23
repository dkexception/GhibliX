package io.github.dkexception.ghiblix.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import io.github.dkexception.ghiblix.app.AppState
import io.github.dkexception.ghiblix.features.home.HomeScreen
import io.github.dkexception.ghiblix.features.movie.MovieScreen
import io.github.dkexception.ghiblix.features.settings.SettingsScreen
import io.github.dkexception.ghiblix.features.watchlist.WatchlistScreen
import io.github.dkexception.ghiblix.shared.viewmodel.home.HomeScreenState
import io.github.dkexception.ghiblix.shared.viewmodel.home.HomeViewModel
import io.github.dkexception.ghiblix.shared.viewmodel.movie.MovieScreenState
import io.github.dkexception.ghiblix.shared.viewmodel.movie.MovieViewModel
import io.github.dkexception.ghiblix.shared.viewmodel.watchlist.WatchlistScreenState
import io.github.dkexception.ghiblix.shared.viewmodel.watchlist.WatchlistViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun GhiblixNavHost(
    modifier: Modifier = Modifier,
    appState: AppState
) = NavHost(
    modifier = modifier.fillMaxSize(),
    navController = appState.navController,
    startDestination = TopLevelDestination.Home,
    enterTransition = GhiblixNavTransitions.enterTransition,
    exitTransition = GhiblixNavTransitions.exitTransition,
    popEnterTransition = GhiblixNavTransitions.popEnterTransition,
    popExitTransition = GhiblixNavTransitions.popExitTransition
) {

    composable<TopLevelDestination.Home>(
        deepLinks = listOf(
            navDeepLink {
                uriPattern = "https://dkexception.github.io/ghiblix/home"
            }
        )
    ) {
        val viewModel: HomeViewModel = koinViewModel()
        val state: HomeScreenState by viewModel.state.collectAsStateWithLifecycle()
        val searchQuery: String by viewModel.searchQuery.collectAsStateWithLifecycle()
        HomeScreen(
            searchQuery = searchQuery,
            state = state,
            onEvent = viewModel::onEvent,
            onMovieClicked = appState.navController::navigateToMovieDetails,
            onSettingsClicked = appState.navController::navigateToSettings
        )
    }

    composable<TopLevelDestination.Search> {

    }

    composable<TopLevelDestination.Watchlist> {

        val viewModel: WatchlistViewModel = koinViewModel()
        val state: WatchlistScreenState by viewModel.state.collectAsStateWithLifecycle()
        WatchlistScreen(
            state = state,
            onEvent = viewModel::onEvent,
            onMovieClicked = appState.navController::navigateToMovieDetails
        )
    }

    composable<Settings> {
        SettingsScreen()
    }

    composable<Movie>(
        deepLinks = listOf(
            navDeepLink<Movie>(basePath = "https://dkexception.github.io/ghiblix/movie")
        )
    ) { backStackEntry ->
        val viewModel: MovieViewModel = koinViewModel(
            parameters = { parametersOf(backStackEntry.toRoute<Movie>().movieId) }
        )
        val state: MovieScreenState by viewModel.state.collectAsStateWithLifecycle()
        MovieScreen(
            state = state,
            onEvent = viewModel::onEvent,
            onNavigateUp = appState.navController::navigateUp
        )
    }
}
