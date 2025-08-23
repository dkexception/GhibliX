package io.github.dkexception.ghiblix.shared.viewmodel.movie

import androidx.compose.runtime.Stable
import io.github.dkexception.ghiblix.shared.domain.model.Film

@Stable
data class MovieScreenState(

    val film: Film? = null,

    val isWatchlisted: Boolean = false
)
