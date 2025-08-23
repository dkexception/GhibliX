package io.github.dkexception.ghiblix.shared.di

import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import io.github.dkexception.ghiblix.shared.repository.GhiblixRepository
import io.github.dkexception.ghiblix.shared.viewmodel.home.HomeViewModel
import io.github.dkexception.ghiblix.shared.viewmodel.movie.MovieViewModel
import io.github.dkexception.ghiblix.shared.viewmodel.watchlist.WatchlistViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AppContainer : KoinComponent {

    private val repository: GhiblixRepository by inject()

    val homeViewModelFactory = viewModelFactory {
        initializer {
            HomeViewModel(repository = repository)
        }
    }

    val movieViewModelFactory = viewModelFactory {
        initializer {
            // this: CreationExtras
            MovieViewModel(
                filmId = this[MovieViewModel.FILM_ID_KEY] ?: error("Expected Film ID"),
                repository = repository
            )
        }
    }

    val watchlistViewModelFactory = viewModelFactory {
        initializer {
            WatchlistViewModel()
        }
    }
}
