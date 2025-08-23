package io.github.dkexception.ghiblix.app

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.github.dkexception.ghiblix.navigation.TopLevelDestination
import io.github.dkexception.ghiblix.theme.GhiblixTheme
import kotlinx.collections.immutable.PersistentList
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun GhiblixBottomAppBar(
    modifier: Modifier = Modifier,
    topLevelDestinations: PersistentList<TopLevelDestination>,
    navigateToTopLevelDestination: (destination: TopLevelDestination) -> Unit,
    navController: NavHostController = rememberNavController(),
    previewOnlyIsBarVisible: Boolean = false
) {

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination

    val visible = topLevelDestinations.any { destination ->
        true == currentDestination?.hierarchy?.any { it.hasRoute(destination::class) }
    }
//    val routes = remember(topLevelDestinations) { topLevelDestinations.fastMap { it::class } }
//    val visible = currentDestination?.hierarchy.orEmpty().any { h ->
//        routes.any { it::class == h::class }
//    }

    AnimatedContent(
        targetState = visible || previewOnlyIsBarVisible,
        transitionSpec = {
            if (targetState) {
                slideInVertically { it } togetherWith fadeOut()
            } else {
                fadeIn() togetherWith slideOutVertically { it }
            }
        },
        contentAlignment = Alignment.Center
    ) {
        if (it) {

            Column {

                Spacer(
                    modifier = modifier.height(1.dp).background(MaterialTheme.colorScheme.primary)
                )

                NavigationBar(
                    modifier = modifier,
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.onSurface,
                    tonalElevation = 1.dp
                ) {

                    topLevelDestinations.fastForEach { topLevelDestination ->

                        val selected = currentDestination?.hierarchy.orEmpty().any { destination ->
                            destination.hasRoute(topLevelDestination::class)
                        }

                        NavigationBarItem(
                            selected = selected,
                            onClick = {
                                navigateToTopLevelDestination(topLevelDestination)
                            },
                            icon = {
                                Icon(
                                    painter = painterResource(topLevelDestination.iconId),
                                    contentDescription = null
                                )
                            },
                            label = {
                                Text(
                                    text = topLevelDestination.label,
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 1,
//                                style = CoachTheme.typography.labelMedium.copy(fontSize = 10.sp)
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = Color.Transparent,
                                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                }
            }
        } else {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Transparent)
                    .navigationBarsPadding()
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    GhiblixTheme {
        GhiblixBottomAppBar(
            topLevelDestinations = rememberAppState().topLevelDestinations,
            navigateToTopLevelDestination = {},
            previewOnlyIsBarVisible = true
        )
    }
}
