package io.github.dkexception.ghiblix.shared.di

import io.github.dkexception.ghiblix.api.di.apiModule
import io.github.dkexception.ghiblix.images.di.imageModule
import io.github.dkexception.ghiblix.shared.repository.GhiblixRepository
import io.github.dkexception.ghiblix.shared.viewmodel.home.HomeViewModel
import io.github.dkexception.ghiblix.shared.viewmodel.movie.MovieViewModel
import io.github.dkexception.ghiblix.shared.viewmodel.watchlist.WatchlistViewModel
import io.github.dkexception.ghiblix.storage.di.storageModule
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

private val dataModule = module {

    singleOf(::GhiblixRepository)
}

private val viewModelModule = module {

    viewModelOf(::HomeViewModel)

    // Assisted param: filmId
    viewModel { (filmId: String) ->
        MovieViewModel(filmId = filmId, repository = get())
    }

    viewModelOf(::WatchlistViewModel)
}

fun initKoin(
    optionalAppDeclaration: (KoinApplication.() -> Unit)? = null
) {
    startKoin {
        optionalAppDeclaration?.invoke(this)
        modules(imageModule, viewModelModule, dataModule, apiModule, storageModule)
    }
}
