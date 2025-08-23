package io.github.dkexception.ghiblix.app

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.dkexception.ghiblix.navigation.GhiblixNavHost
import io.github.dkexception.ghiblix.shared.di.initKoin
import io.github.dkexception.ghiblix.theme.GhiblixTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(
    modifier: Modifier = Modifier
) = GhiblixTheme {
    AppContent(modifier)
}

@Composable
private fun AppContent(
    modifier: Modifier = Modifier,
    appState: AppState = rememberAppState()
) = Scaffold(
    modifier = modifier,
    bottomBar = {
        GhiblixBottomAppBar(
            modifier = Modifier.fillMaxWidth(),
            topLevelDestinations = appState.topLevelDestinations,
            navigateToTopLevelDestination = appState::navigateToTopLevelDestination,
            navController = appState.navController
        )
    },
//        snackbarHost = { CoachSnackbarHost(state = it) }
) { pv ->
    GhiblixNavHost(
        modifier = Modifier.padding(pv),
        appState = appState
    )
}

@Preview
@Composable
private fun AppPreview() {
    initKoin()
    AppContent()
}
