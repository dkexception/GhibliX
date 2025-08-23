package io.github.dkexception.ghiblix

import android.app.Application
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import io.github.dkexception.ghiblix.images.GhiblixImageLoader
import io.github.dkexception.ghiblix.shared.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GhiblixApplication : Application(), SingletonImageLoader.Factory, KoinComponent {

    private val imageLoader: GhiblixImageLoader by inject()

    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidLogger()
            androidContext(this@GhiblixApplication)
        }
    }

    override fun newImageLoader(context: PlatformContext): ImageLoader = imageLoader()
}
