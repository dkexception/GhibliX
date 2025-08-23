package io.github.dkexception.ghiblix.images

import android.content.Context
import coil3.ImageLoader
import coil3.request.crossfade

class GhiblixImageLoader(
    val context: Context
) {

    operator fun invoke(): ImageLoader = ImageLoader.Builder(context)
        .crossfade(true)
        .build()
}
