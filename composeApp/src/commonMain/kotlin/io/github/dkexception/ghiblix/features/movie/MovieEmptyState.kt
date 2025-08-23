package io.github.dkexception.ghiblix.features.movie

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ghiblix.composeapp.generated.resources.Res
import ghiblix.composeapp.generated.resources.ill_generic_empty_state
import ghiblix.composeapp.generated.resources.movie_not_found_subtitle
import ghiblix.composeapp.generated.resources.movie_not_found_title
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun MovieEmptyState() = Column(
    modifier = Modifier.fillMaxSize().padding(16.dp),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
) {
    Image(
        painter = painterResource(Res.drawable.ill_generic_empty_state),
        contentDescription = null
    )
    Spacer(Modifier.height(8.dp))
    Text(stringResource(Res.string.movie_not_found_title), textAlign = TextAlign.Center)
    Spacer(Modifier.height(8.dp))
    Text(stringResource(Res.string.movie_not_found_subtitle), textAlign = TextAlign.Center)
}
