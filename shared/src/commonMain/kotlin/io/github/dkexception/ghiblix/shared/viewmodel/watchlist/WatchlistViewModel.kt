package io.github.dkexception.ghiblix.shared.viewmodel.watchlist

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class WatchlistViewModel : ViewModel() {

    private val _state = MutableStateFlow(WatchlistScreenState())
    val state: StateFlow<WatchlistScreenState> = _state.asStateFlow()

    fun onEvent(event: WatchlistEvent) = Unit
}
