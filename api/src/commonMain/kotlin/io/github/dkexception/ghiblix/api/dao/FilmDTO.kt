package io.github.dkexception.ghiblix.api.dao

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FilmDTO(
    @SerialName("description")
    val description: String?,
    @SerialName("director")
    val director: String?,
    @SerialName("id")
    val id: String?,
    @SerialName("image")
    val image: String?,
    @SerialName("locations")
    val locations: List<String?>?,
    @SerialName("movie_banner")
    val movieBanner: String?,
    @SerialName("original_title")
    val originalTitle: String?,
    @SerialName("original_title_romanised")
    val originalTitleRomanised: String?,
    @SerialName("people")
    val people: List<String?>?,
    @SerialName("producer")
    val producer: String?,
    @SerialName("release_date")
    val releaseDate: String?,
    @SerialName("rt_score")
    val rtScore: String?,
    @SerialName("running_time")
    val runningTime: String?,
    @SerialName("species")
    val species: List<String?>?,
    @SerialName("title")
    val title: String?,
    @SerialName("url")
    val url: String?,
    @SerialName("vehicles")
    val vehicles: List<String?>?
)
