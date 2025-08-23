@file:OptIn(ExperimentalUuidApi::class, ExperimentalMaterial3Api::class)

package io.github.dkexception.ghiblix.features.movie

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import ghiblix.composeapp.generated.resources.Res
import ghiblix.composeapp.generated.resources.ic_bookmark
import ghiblix.composeapp.generated.resources.ic_bookmark_filled
import io.github.dkexception.ghiblix.shared.domain.model.Film
import io.github.dkexception.ghiblix.shared.viewmodel.movie.MovieEvent
import io.github.dkexception.ghiblix.shared.viewmodel.movie.MovieScreenState
import io.github.dkexception.ghiblix.theme.GhiblixTheme
import io.github.dkexception.ghiblix.ui.BackButton
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.uuid.ExperimentalUuidApi

@Composable
fun MovieScreen(
    state: MovieScreenState,
    onEvent: (MovieEvent) -> Unit,
    onNavigateUp: () -> Unit
) = MovieScreenContent(state, onEvent, onNavigateUp)

@Composable
private fun MovieScreenContent(
    state: MovieScreenState,
    onEvent: (MovieEvent) -> Unit,
    onNavigateUp: () -> Unit
) {

    val film: Film? = state.film

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        // Title bar
        CenterAlignedTopAppBar(
            modifier = Modifier.fillMaxWidth(),
            title = {
                Text(
                    film?.title.orEmpty()
                )
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.background,
                titleContentColor = MaterialTheme.colorScheme.onBackground,
                actionIconContentColor = MaterialTheme.colorScheme.onBackground,
                navigationIconContentColor = MaterialTheme.colorScheme.onBackground
            ),
            windowInsets = WindowInsets(0, 0, 0, 0),
            navigationIcon = {
                BackButton(onNavigateUp)
            },
            actions = {
                film?.let {
                    IconButton(
                        onClick = { onEvent(MovieEvent.WatchlistToggle) }
                    ) {
                        Icon(
                            painter = painterResource(
                                if (state.isWatchlisted) {
                                    Res.drawable.ic_bookmark_filled
                                } else {
                                    Res.drawable.ic_bookmark
                                }
                            ),
                            contentDescription = null
                        )
                    }
                }
            }
        )

        if (film == null) {
            MovieEmptyState()
        } else {
            MovieDetails(film)
        }
    }
}

@Composable
private fun MovieDetails(
    film: Film
) = Column(
    modifier = Modifier.fillMaxSize()
) {
    AsyncImage(
        modifier = Modifier.fillMaxWidth(),
        model = film.bannerUrl,
        contentDescription = null,
        contentScale = ContentScale.FillWidth
    )
}

@Preview
@Composable
private fun MovieScreenPreview() = GhiblixTheme {
    MovieScreenContent(
        state = MovieScreenState(
            film = Film(
                id = "2baf70d1-42bb-4437-b551-e5fed5a87abe",
                title = "Castle in the Sky",
                originalTitle = "天空の城ラピュタ",
                originalTitleRomanised = "Tenkū no shiro Rapyuta",
                description = "The orphan Sheeta inherited a mysterious crystal that links her to the mythical sky-kingdom of Laputa...",
                director = "Hayao Miyazaki",
                producer = "Isao Takahata",
                releaseYear = "1986",
                rating = 95,
                runningTimeMinutes = 124,
                image = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/npOnzAbLh6VOIu3naU5QaEcTepo.jpg",
                bannerUrl = "https://image.tmdb.org/t/p/w533_and_h300_bestv2/3cyjYtLWCBE1uvWINHFsFnE8LUK.jpg",
                url = "https://ghibliapi.vercel.app/films/2baf70d1-42bb-4437-b551-e5fed5a87abe",
                locations = listOf("https://ghibliapi.vercel.app/locations/"),
                people = listOf("https://ghibliapi.vercel.app/people/598f7048-74ff-41e0-92ef-87dc1ad980a9"),
                species = listOf("https://ghibliapi.vercel.app/species/af3910a6-429f-4c74-9ad5-dfe1c4aa04f2"),
                vehicles = listOf("https://ghibliapi.vercel.app/vehicles/4e09b023-f650-4747-9ab9-eacf14540cfb")
            )
        ),
        onEvent = {},
        onNavigateUp = {}
    )
}
