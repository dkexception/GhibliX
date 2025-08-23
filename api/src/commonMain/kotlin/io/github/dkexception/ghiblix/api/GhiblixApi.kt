package io.github.dkexception.ghiblix.api

import io.github.dkexception.ghiblix.api.dao.FilmDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

interface GhiblixApi {

    fun setBaseUrl(url: String)

    suspend fun getAllFilms(): List<FilmDTO>

    suspend fun getFilmById(filmId: String): FilmDTO
}

internal class GhiblixApiImpl(
    val ktorClient: HttpClient
) : GhiblixApi {

    private var mBaseUrl: String = "about:blank"

    override fun setBaseUrl(url: String) {
        mBaseUrl = url
    }

    override suspend fun getAllFilms(): List<FilmDTO> = ktorClient.get("${mBaseUrl}films").body()

    override suspend fun getFilmById(filmId: String): FilmDTO =
        ktorClient.get("${mBaseUrl}films/$filmId").body()
}
