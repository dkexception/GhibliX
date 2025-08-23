package io.github.dkexception.ghiblix.shared.viewmodel.watchlist

import androidx.compose.runtime.Stable
import io.github.dkexception.ghiblix.shared.domain.model.Film
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Stable
data class WatchlistScreenState(

    val films: ImmutableList<Film> = persistentListOf()
)
