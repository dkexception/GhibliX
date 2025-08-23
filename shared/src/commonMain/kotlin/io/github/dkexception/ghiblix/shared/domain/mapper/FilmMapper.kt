package io.github.dkexception.ghiblix.shared.domain.mapper

import io.github.dkexception.ghiblix.api.dao.FilmDTO
import io.github.dkexception.ghiblix.shared.domain.model.Film

fun FilmDTO.toFilm(): Film = Film(
    id = id.orEmpty(),
    title = title.orEmpty(),
    originalTitle = originalTitle.orEmpty(),
    originalTitleRomanised = originalTitleRomanised.orEmpty(),
    description = description.orEmpty(),
    director = director.orEmpty(),
    producer = producer.orEmpty(),
    releaseYear = releaseDate.orEmpty(),
    rating = rtScore?.toIntOrNull() ?: 0,
    runningTimeMinutes = runningTime?.toIntOrNull() ?: 0,
    image = image.orEmpty(),
    bannerUrl = movieBanner.orEmpty(),
    url = url.orEmpty(),
    locations = locations?.filterNotNull() ?: emptyList(),
    people = people?.filterNotNull() ?: emptyList(),
    species = species?.filterNotNull() ?: emptyList(),
    vehicles = vehicles?.filterNotNull() ?: emptyList()
)
