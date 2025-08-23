package io.github.dkexception.ghiblix.features.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import ghiblix.composeapp.generated.resources.Res
import ghiblix.composeapp.generated.resources.home_subtitle
import ghiblix.composeapp.generated.resources.ic_settings
import io.github.dkexception.ghiblix.shared.domain.model.Film
import io.github.dkexception.ghiblix.shared.viewmodel.home.HomeEvent
import io.github.dkexception.ghiblix.shared.viewmodel.home.HomeScreenState
import io.github.dkexception.ghiblix.theme.GhiblixTheme
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun HomeScreen(
    searchQuery: String,
    state: HomeScreenState,
    onEvent: (HomeEvent) -> Unit,
    onMovieClicked: (id: String) -> Unit,
    onSettingsClicked: () -> Unit
) = HomeScreenContent(searchQuery, state, onEvent, onMovieClicked, onSettingsClicked)

@Composable
private fun HomeScreenContent(
    searchQuery: String,
    state: HomeScreenState,
    onEvent: (HomeEvent) -> Unit,
    onMovieClicked: (id: String) -> Unit,
    onSettingsClicked: () -> Unit
) = Column(
    modifier = Modifier
        .fillMaxSize()
        .padding(top = 16.dp, start = 16.dp, end = 16.dp)
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(Res.string.home_subtitle),
            fontSize = 16.sp
        )

        IconButton(
            onClick = onSettingsClicked
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_settings),
                contentDescription = null
            )
        }
    }

    Spacer(Modifier.height(16.dp))

    TextField(
        value = searchQuery,
        onValueChange = { onEvent(HomeEvent.OnSearchQueryChange(it)) },
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(Modifier.height(16.dp))

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 120.dp)
    ) {
        items(
            items = state.films
        ) { film ->
            Card(
                modifier = Modifier.padding(8.dp),
                onClick = {
                    onMovieClicked(film.id)
                }
            ) {
                AsyncImage(model = film.image, contentDescription = null)
            }
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {

    val previewFilms = persistentListOf(
        Film(
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
        ),
        Film(
            id = "12cfb892-aac0-4c5b-94af-521852e46d6a",
            title = "Grave of the Fireflies",
            originalTitle = "火垂るの墓",
            originalTitleRomanised = "Hotaru no haka",
            description = "A boy and his sister, orphaned during WWII, are left to survive on their own in Japan...",
            director = "Isao Takahata",
            producer = "Toru Hara",
            releaseYear = "1988",
            rating = 97,
            runningTimeMinutes = 89,
            image = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/qG3RYlIVpTYclR9TYIsy8p7m7AT.jpg",
            bannerUrl = "https://image.tmdb.org/t/p/original/vkZSd0Lp8iCVBGpFH9L7LzLusjS.jpg",
            url = "https://ghibliapi.vercel.app/films/12cfb892-aac0-4c5b-94af-521852e46d6a",
            locations = listOf("https://ghibliapi.vercel.app/locations/"),
            people = listOf("https://ghibliapi.vercel.app/people/"),
            species = listOf("https://ghibliapi.vercel.app/species/af3910a6-429f-4c74-9ad5-dfe1c4aa04f2"),
            vehicles = listOf("https://ghibliapi.vercel.app/vehicles/")
        ),
        Film(
            id = "58611129-2dbc-4a81-a72f-77ddfc1b1b49",
            title = "My Neighbor Totoro",
            originalTitle = "となりのトトロ",
            originalTitleRomanised = "Tonari no Totoro",
            description = "Two sisters move to the country with their father and discover magical forest spirits called Totoros...",
            director = "Hayao Miyazaki",
            producer = "Hayao Miyazaki",
            releaseYear = "1988",
            rating = 93,
            runningTimeMinutes = 86,
            image = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/rtGDOeG9LzoerkDGZF9dnVeLppL.jpg",
            bannerUrl = "https://image.tmdb.org/t/p/original/etqr6fOOCXQOgwrQXaKwenTSuzx.jpg",
            url = "https://ghibliapi.vercel.app/films/58611129-2dbc-4a81-a72f-77ddfc1b1b49",
            locations = listOf("https://ghibliapi.vercel.app/locations/"),
            people = listOf("https://ghibliapi.vercel.app/people/986faac6-67e3-4fb8-a9ee-bad077c2e7fe"),
            species = listOf("https://ghibliapi.vercel.app/species/af3910a6-429f-4c74-9ad5-dfe1c4aa04f2"),
            vehicles = listOf("https://ghibliapi.vercel.app/vehicles/")
        )
    )

    GhiblixTheme {
        HomeScreen(
            searchQuery = "Castle",
            state = HomeScreenState(
                films = previewFilms
            ),
            onEvent = {},
            onMovieClicked = {},
            onSettingsClicked = {}
        )
    }
}
