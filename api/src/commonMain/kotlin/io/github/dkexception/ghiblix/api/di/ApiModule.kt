package io.github.dkexception.ghiblix.api.di

import io.github.dkexception.ghiblix.api.GhiblixApi
import io.github.dkexception.ghiblix.api.GhiblixApiImpl
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val apiModule = module {

    factory {
        HttpClient {
            install(ContentNegotiation) {
                json(json, contentType = ContentType.Any)
            }
        }
    }

    singleOf(::GhiblixApiImpl) bind (GhiblixApi::class)
}

val json = Json { ignoreUnknownKeys = true }
