package io.github.dkexception.ghiblix.navigation

import ghiblix.composeapp.generated.resources.Res
import ghiblix.composeapp.generated.resources.ic_bookmark
import ghiblix.composeapp.generated.resources.ic_home
import ghiblix.composeapp.generated.resources.ic_search
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.DrawableResource

sealed interface Routes

@Serializable
object Settings : Routes

@Serializable
data class Movie(val movieId: String) : Routes

@Serializable
sealed class TopLevelDestination(
    @Contextual val iconId: DrawableResource,
    val label: String
) : Routes {

    @Serializable
    data object Home : TopLevelDestination(
        iconId = Res.drawable.ic_home,
        label = "Home"
    )

    @Serializable
    data object Search : TopLevelDestination(
        iconId = Res.drawable.ic_search,
        label = "Search"
    )

    @Serializable
    data object Watchlist : TopLevelDestination(
        iconId = Res.drawable.ic_bookmark,
        label = "Watchlist"
    )
}
