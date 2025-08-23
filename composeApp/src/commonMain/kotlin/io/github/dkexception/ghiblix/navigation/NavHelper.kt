package io.github.dkexception.ghiblix.navigation

import androidx.navigation.NavController

fun NavController.navigateToMovieDetails(
    id: String
) {
    navigate(Movie(id))
}

fun NavController.navigateToSettings() = navigate(Settings)
