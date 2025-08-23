package io.github.dkexception.ghiblix.ui

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import ghiblix.composeapp.generated.resources.Res
import ghiblix.composeapp.generated.resources.ic_chevron_left
import org.jetbrains.compose.resources.painterResource

@Composable
fun BackButton(
    onClick: () -> Unit
) = IconButton(
    onClick = onClick
) {
    Icon(
        painter = painterResource(Res.drawable.ic_chevron_left),
        contentDescription = null
    )
}
