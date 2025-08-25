package io.github.dkexception.ghiblix.shared.repository

import io.github.dkexception.ghiblix.api.GhiblixApi
import io.github.dkexception.ghiblix.api.di.json
import io.github.dkexception.ghiblix.shared.domain.mapper.toFilm
import io.github.dkexception.ghiblix.shared.domain.model.Film
import io.github.dkexception.ghiblix.shared.utils.GhiblixResult
import io.github.dkexception.ghiblix.storage.contract.IGhiblixDatastore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

private const val MOVIES_DATA_KEY = "movies_data_key"
private const val WATCHLIST_MOVIES_IDS_KEY = "watchlist_movies_ids_key"

class GhiblixRepository(
    private val api: GhiblixApi,
    private val datastore: IGhiblixDatastore
) {

    init {
        api.setBaseUrl("https://ghibliapi.vercel.app/")
    }

    fun getAllFilms(): Flow<GhiblixResult<List<Film>>> = flow {

        emit(GhiblixResult.Loading)

        try {
            val films =
                getCachedFilms() ?: getFilmsFromInternet() ?: error("Unable to fetch films!")
            emit(GhiblixResult.Success(films))
        } catch (e: Exception) {
            emit(GhiblixResult.Failure(e))
        }
    }

    private suspend fun getCachedFilms(): List<Film>? = try {

        val existingFilms: String = datastore.getString(
            key = MOVIES_DATA_KEY
        ).takeUnless(String::isBlank) ?: return null

        json.decodeFromString<List<Film>>(existingFilms)

    } catch (_: Exception) {
        null
    }

    private suspend fun getFilmsFromInternet(): List<Film>? = try {
        api.getAllFilms().map { it.toFilm() }.apply {
            if (this.isNotEmpty()) {
                storeFilmsToCache(this)
            }
        }
    } catch (_: Exception) {
        null
    }

    private suspend fun storeFilmsToCache(films: List<Film>) = try {
        datastore.setString(
            key = MOVIES_DATA_KEY,
            value = json.encodeToString<List<Film>>(films)
        )
    } catch (_: Exception) {
        // do nothing
    }

    suspend fun getFilmById(filmId: String): GhiblixResult<Film> = try {
        GhiblixResult.Success(
            getFilmFromCache(filmId) ?: api.getFilmById(filmId).toFilm()
        )
    } catch (e: Exception) {
        GhiblixResult.Failure(e)
    }

    private suspend fun getFilmFromCache(filmId: String): Film? = try {
        getCachedFilms()?.find { it.id == filmId }
    } catch (_: Exception) {
        null
    }

    suspend fun isFilmWatchlisted(filmId: String): Boolean = try {

        val existingWatchlistedFilmIdsString: String = datastore.getString(
            key = WATCHLIST_MOVIES_IDS_KEY
        ).takeUnless(String::isBlank) ?: return false

        json.decodeFromString<List<String>>(
            existingWatchlistedFilmIdsString
        ).any { it.equals(filmId, true) }

    } catch (_: Exception) {
        false
    }

    suspend fun markFilmWatchlisted(filmId: String) = try {

        val existingWatchlistedFilmIds: List<String> = datastore.getString(
            key = WATCHLIST_MOVIES_IDS_KEY
        ).takeUnless(String::isBlank)?.let {
            json.decodeFromString<List<String>>(it)
        }.orEmpty()

        val listToPersist: List<String> = existingWatchlistedFilmIds
            .toMutableList()
            .apply {
                add(filmId)
                toList()
            }

        datastore.setString(
            key = WATCHLIST_MOVIES_IDS_KEY,
            value = json.encodeToString<List<String>>(listToPersist)
        )

    } catch (_: Exception) {
        // do nothing
    }

    suspend fun removeFilmWatchlisted(filmId: String) = try {

        val existingWatchlistedFilmIds: List<String> = datastore.getString(
            key = WATCHLIST_MOVIES_IDS_KEY
        ).takeUnless(String::isBlank)?.let {
            json.decodeFromString<List<String>>(it)
        }.orEmpty()

        val listToPersist: List<String> = existingWatchlistedFilmIds
            .toMutableList()
            .apply {
                remove(filmId)
                toList()
            }

        datastore.setString(
            key = WATCHLIST_MOVIES_IDS_KEY,
            value = json.encodeToString<List<String>>(listToPersist)
        )

    } catch (_: Exception) {
        // do nothing
    }
}
