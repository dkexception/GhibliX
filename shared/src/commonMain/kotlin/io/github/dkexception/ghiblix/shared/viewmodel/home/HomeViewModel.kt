package io.github.dkexception.ghiblix.shared.viewmodel.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.dkexception.ghiblix.shared.repository.GhiblixRepository
import io.github.dkexception.ghiblix.shared.utils.GhiblixResult
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlin.time.Duration.Companion.seconds

class HomeViewModel(
    repository: GhiblixRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    val state: StateFlow<HomeScreenState> =
        repository
            .getAllFilms()
            .combine(_searchQuery) { filmsResult, query ->
                HomeScreenState(
                    isLoading = filmsResult is GhiblixResult.Loading,
                    isError = filmsResult is GhiblixResult.Failure,
                    films = (filmsResult as? GhiblixResult.Success)?.data
                        ?.filter { it.title.contains(query, ignoreCase = true) }
                        ?.toPersistentList()
                        ?: persistentListOf()
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeout = 10.seconds),
                initialValue = HomeScreenState()
            )

    fun onEvent(homeEvent: HomeEvent) {
        when (homeEvent) {
            is HomeEvent.OnSearchQueryChange -> _searchQuery.update { homeEvent.query }
        }
    }
}
