package io.github.dkexception.ghiblix.shared.viewmodel.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import io.github.dkexception.ghiblix.shared.domain.model.Film
import io.github.dkexception.ghiblix.shared.repository.GhiblixRepository
import io.github.dkexception.ghiblix.shared.utils.GhiblixResult
import io.github.dkexception.ghiblix.shared.utils.fold
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MovieViewModel(
    private val filmId: String,
    private val repository: GhiblixRepository
) : ViewModel() {

    private val _state = MutableStateFlow(MovieScreenState())
    val state: StateFlow<MovieScreenState> = _state
        .onStart {
            getFilm()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = MovieScreenState()
        )

    private fun getFilm() = viewModelScope.launch {

        val (filmResult, isWatchlisted) = run {
            val filmDeferred = async { repository.getFilmById(filmId) }
            val watchlistDeferred = async { repository.isFilmWatchlisted(filmId) }
            val results = awaitAll(filmDeferred, watchlistDeferred)
            @Suppress("UNCHECKED_CAST")
            (results[0] as GhiblixResult<Film>) to (results[1] as Boolean)
        }

        filmResult.fold(
            onLoading = {},
            onFailure = {
                // TODO
            },
            onSuccess = { film ->
                _state.update {
                    it.copy(
                        film = film,
                        isWatchlisted = isWatchlisted
                    )
                }
            }
        )
    }

    fun onEvent(movieEvent: MovieEvent) {
        when (movieEvent) {
            MovieEvent.WatchlistToggle -> viewModelScope.launch {
                if (state.value.isWatchlisted) {
                    repository.removeFilmWatchlisted(filmId)
                } else {
                    repository.markFilmWatchlisted(filmId)
                }
                getFilm()
            }
        }
    }

    companion object {
        val FILM_ID_KEY = CreationExtras.Key<String>()
    }
}
