package io.github.dkexception.ghiblix.shared.viewmodel.movie

sealed class MovieEvent {

    object WatchlistToggle : MovieEvent()
}
