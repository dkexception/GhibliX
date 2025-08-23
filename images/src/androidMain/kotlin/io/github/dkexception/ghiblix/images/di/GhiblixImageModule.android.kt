package io.github.dkexception.ghiblix.images.di

import io.github.dkexception.ghiblix.images.GhiblixImageLoader
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val imageModule: Module = module {

    singleOf(::GhiblixImageLoader)
}
