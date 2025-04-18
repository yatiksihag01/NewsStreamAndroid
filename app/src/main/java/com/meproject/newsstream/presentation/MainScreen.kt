package com.meproject.newsstream.presentation

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.window.core.layout.WindowWidthSizeClass
import com.meproject.newsstream.R
import com.meproject.newsstream.presentation.ui.bookmark.Bookmark
import com.meproject.newsstream.presentation.ui.bookmark.bookmarkScreenDestination
import com.meproject.newsstream.presentation.ui.explore.Explore
import com.meproject.newsstream.presentation.ui.explore.exploreScreenDestination
import com.meproject.newsstream.presentation.ui.home.Home
import com.meproject.newsstream.presentation.ui.home.homeScreenDestination
import com.meproject.newsstream.presentation.ui.theme.NewsStreamTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    var selectedDestination by remember { mutableStateOf<NavigationDestination>(Home) }

    val windowWidthSizeClass = currentWindowAdaptiveInfo().windowSizeClass.windowWidthSizeClass
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            val currentDestination = navBackStackEntry?.destination
            AppDestinations.entries.forEach { appDestination ->
                val isSelected = currentDestination?.hierarchy?.any {
                    it.route == appDestination.destination::class.qualifiedName
                } == true
                if (isSelected) selectedDestination = appDestination.destination
                item(icon = {
                    Icon(
                        appDestination.destination.getIcon(isSelected = isSelected),
                        contentDescription = stringResource(appDestination.description)
                    )
                },
                    label = { Text(stringResource(id = appDestination.description)) },
                    selected = isSelected,
                    onClick = {
                        if (currentDestination != appDestination.destination) {
                            navController.navigate(appDestination.destination) {
                                popUpTo(Home) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    })
            }
        }, layoutType = if (windowWidthSizeClass == WindowWidthSizeClass.COMPACT) {
            NavigationSuiteType.NavigationBar
        } else NavigationSuiteType.NavigationRail
    ) {

        Scaffold(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection), topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        topAppBarTitle(currentDestination = selectedDestination),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }, scrollBehavior = scrollBehavior
            )
        }) { paddingValues ->
            NavHost(
                modifier = Modifier.padding(paddingValues),
                navController = navController,
                startDestination = Home
            ) {
                homeScreenDestination()
                exploreScreenDestination()
                bookmarkScreenDestination()
            }
        }
    }
}

@Composable
private fun topAppBarTitle(currentDestination: NavigationDestination): String {
    return when (currentDestination) {
        Home -> stringResource(R.string.trending_now)
        Explore -> stringResource(R.string.for_you)
        Bookmark -> stringResource(R.string.saved_articles)
        else -> stringResource(id = R.string.account)
    }
}

private fun NavigationDestination.getIcon(isSelected: Boolean): ImageVector {
    return when (this) {
        Home -> if (isSelected) Icons.Filled.Home else Icons.Outlined.Home
        Explore -> if (isSelected) Icons.Filled.Explore else Icons.Outlined.Explore
        Bookmark -> if (isSelected) Icons.Filled.Bookmarks else Icons.Outlined.Bookmarks
        else -> if (isSelected) Icons.Filled.Person else Icons.Outlined.Person
    }

}

private enum class AppDestinations(
    val destination: NavigationDestination, @StringRes val description: Int
) {
    HOME(Home, R.string.home), EXPLORE(Explore, R.string.explore), BOOKMARK(
        Bookmark,
        R.string.bookmark
    ),
}

@Preview
@Preview(
    name = "Dark Mode Tablet",
    device = "spec:id=reference_tablet,shape=Normal,width=1280,height=800,unit=dp,dpi=240",
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun MainScreenPreview() {
    NewsStreamTheme {
        Surface {
            MainScreen()
        }
    }
}