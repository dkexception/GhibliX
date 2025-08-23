package io.github.dkexception.ghiblix.shared.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Film(
    val id: String,
    val title: String,
    val originalTitle: String,
    val originalTitleRomanised: String,
    val description: String,
    val director: String,
    val producer: String,
    val releaseYear: String,
    val rating: Int,
    val runningTimeMinutes: Int,
    val image: String,
    val bannerUrl: String,
    val url: String,
    val locations: List<String>,
    val people: List<String>,
    val species: List<String>,
    val vehicles: List<String>
)
