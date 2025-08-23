package io.github.dkexception.ghiblix.shared.viewmodel.home

sealed class HomeEvent {

    data class OnSearchQueryChange(val query: String) : HomeEvent()
}
