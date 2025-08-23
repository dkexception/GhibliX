package io.github.dkexception.ghiblix.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable

@Composable
fun GhiblixTheme(
    darkTheme: Boolean = true,
    colors: ColorScheme = if (darkTheme) darkColors else lightColors,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}
