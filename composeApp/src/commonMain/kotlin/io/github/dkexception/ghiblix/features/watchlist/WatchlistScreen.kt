@file:OptIn(ExperimentalMaterial3Api::class)

package io.github.dkexception.ghiblix.features.watchlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ghiblix.composeapp.generated.resources.Res
import ghiblix.composeapp.generated.resources.ill_empty_watchlist
import ghiblix.composeapp.generated.resources.watchlist_no_movie_subtitle
import ghiblix.composeapp.generated.resources.watchlist_no_movie_title
import ghiblix.composeapp.generated.resources.watchlist_title
import io.github.dkexception.ghiblix.shared.viewmodel.watchlist.WatchlistEvent
import io.github.dkexception.ghiblix.shared.viewmodel.watchlist.WatchlistScreenState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun WatchlistScreen(
    state: WatchlistScreenState,
    onEvent: (WatchlistEvent) -> Unit,
    onMovieClicked: (id: String) -> Unit
) = WatchlistScreenContent(state, onEvent, onMovieClicked)

@Composable
private fun WatchlistScreenContent(
    state: WatchlistScreenState,
    onEvent: (WatchlistEvent) -> Unit,
    onMovieClicked: (id: String) -> Unit
) = Column(
    modifier = Modifier.fillMaxSize()
) {

    // Title bar
    CenterAlignedTopAppBar(
        modifier = Modifier.fillMaxWidth(),
        title = {
            Text(stringResource(Res.string.watchlist_title))
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.onBackground
        ),
        windowInsets = WindowInsets(0, 0, 0, 0)
    )
    HorizontalDivider(
        modifier = Modifier.fillMaxWidth(),
        thickness = Dp.Hairline,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )

    // Actual screen content
    if (state.films.isEmpty()) {
        EmptyState()
    }
}

@Composable
private fun EmptyState() = Column(
    modifier = Modifier.fillMaxSize().padding(16.dp),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
) {
    Image(
        painter = painterResource(Res.drawable.ill_empty_watchlist),
        contentDescription = null
    )
    Spacer(Modifier.height(8.dp))
    Text(stringResource(Res.string.watchlist_no_movie_title), textAlign = TextAlign.Center)
    Spacer(Modifier.height(8.dp))
    Text(stringResource(Res.string.watchlist_no_movie_subtitle), textAlign = TextAlign.Center)
}

@Preview
@Composable
private fun WatchlistScreenEmptyPreview() = WatchlistScreen(
    state = WatchlistScreenState(),
    onEvent = {},
    onMovieClicked = {}
)
