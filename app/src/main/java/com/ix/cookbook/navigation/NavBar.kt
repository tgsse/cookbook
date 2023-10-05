package com.ix.cookbook.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material.icons.outlined.SentimentVerySatisfied
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ix.cookbook.R
import com.ix.cookbook.navigation.util.NavigationTabWithIcon

private val mainTabs = listOf(
    NavigationTabWithIcon(
        route = Routes.RecipesList.route,
        label = R.string.screen_recipes,
        icon = Icons.Outlined.Restaurant,
    ),
    NavigationTabWithIcon(
        route = Routes.FavoriteRecipes.route,
        label = R.string.screen_favorite_recipes,
        icon = Icons.Outlined.Favorite,
    ),
    NavigationTabWithIcon(
        route = Routes.FoodJoke.route,
        label = R.string.screen_food_joke,
        icon = Icons.Outlined.SentimentVerySatisfied,
    ),
)

@Composable
fun NavBar(navController: NavController) {
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        mainTabs.forEach { screen ->
            NavigationBarItem(
                icon = {
                    Icon(
                        screen.icon,
                        contentDescription = null,
                    )
                },
                label = { Text(stringResource(screen.label)) },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                },
            )
        }
    }
}
