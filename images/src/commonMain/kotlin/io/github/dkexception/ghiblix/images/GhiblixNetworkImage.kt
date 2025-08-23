package io.github.dkexception.ghiblix.images

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil3.compose.AsyncImage

@Composable
fun GhiblixNetworkImage(
    imageUrl: String,
    optionalContentDescription: String? = null,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = imageUrl,
        contentDescription = optionalContentDescription,
        modifier = modifier
    )
}
