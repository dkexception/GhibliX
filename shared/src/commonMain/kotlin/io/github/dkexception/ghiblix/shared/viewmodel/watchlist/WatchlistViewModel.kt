package io.github.dkexception.ghiblix.shared.viewmodel.watchlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.dkexception.ghiblix.shared.repository.GhiblixRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class WatchlistViewModel(
    private val repository: GhiblixRepository
) : ViewModel() {

    private val _state = MutableStateFlow(WatchlistScreenState())
    val state: StateFlow<WatchlistScreenState> = _state
        .onStart {

        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = WatchlistScreenState()
        )

    fun onEvent(event: WatchlistEvent) = Unit
}
