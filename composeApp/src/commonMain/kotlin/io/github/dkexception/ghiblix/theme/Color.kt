package io.github.dkexception.ghiblix.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

private val primary = Color(0xFF0296E5)
private val background = Color(0xFF242A32)
private val dullGrey = Color(0xFF67686D)
private val textOffWhite = Color(0xFFEEEEEE)

val lightColors = lightColorScheme(
    primary = primary,
    background = background,
    onSurfaceVariant = dullGrey,
    onBackground = textOffWhite
)

val darkColors = darkColorScheme(
    primary = primary,
    background = background,
    onSurfaceVariant = dullGrey,
    onBackground = textOffWhite
)
