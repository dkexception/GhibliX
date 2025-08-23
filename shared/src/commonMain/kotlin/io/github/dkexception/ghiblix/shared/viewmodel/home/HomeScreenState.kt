package io.github.dkexception.ghiblix.shared.viewmodel.home

import androidx.compose.runtime.Stable
import io.github.dkexception.ghiblix.shared.domain.model.Film
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Stable
data class HomeScreenState(

    val isLoading: Boolean = false,

    val isError: Boolean = false,

    val films: ImmutableList<Film> = persistentListOf()
)
