package com.ix.cookbook.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ix.cookbook.screens.favorites.FavoriteRecipesScreen
import com.ix.cookbook.screens.joke.FoodJokeScreen
import com.ix.cookbook.screens.recipes.RecipesEvent
import com.ix.cookbook.screens.recipes.RecipesScreen
import com.ix.cookbook.screens.recipes.RecipesViewModel
import com.ix.cookbook.screens.recipes.details.RecipeDetails

@Composable
fun NavigationHost(
    navController: NavHostController,
) {
    NavHost(
        navController,
        startDestination = Routes.Main.route,
        route = Routes.Root.route,
    ) {

        fun onNavigateToDetails() {
            navController.navigate(
                route = Routes.RecipeDetails.route,
            ) {
                popUpTo(Routes.Main.route)
            }
        }

        composable(Routes.Main.route) { entry ->
            val viewModel = entry.sharedViewModel<RecipesViewModel>(navController = navController)
            BottomTabNavHost(viewModel = viewModel, onNavigateToDetails = { onNavigateToDetails() })
        }

        composable(Routes.RecipeDetails.route) { entry ->
            val viewModel = entry.sharedViewModel<RecipesViewModel>(navController = navController)
            val state = viewModel.state.collectAsStateWithLifecycle()
            val isFavorite = if (state.value.selectedRecipe != null) {
                state.value.favoriteRecipes.find { it.externalId == state.value.selectedRecipe!!.id } != null
            } else {
                false
            }

            RecipeDetails(
                selectedRecipe = state.value.selectedRecipe,
                onNavigateBack = { navController.popBackStack() },
                isFavorite = isFavorite,
                onFavoriteClick = { recipe -> viewModel.onEvent(RecipesEvent.Favorite(recipe)) },
            )
        }
    }
}

@Composable
fun BottomTabNavHost(viewModel: RecipesViewModel, onNavigateToDetails: () -> Unit) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            NavBar(navController)
        },
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = Routes.RecipesList.route,
            Modifier.padding(innerPadding),
        ) {
            composable(Routes.RecipesList.route) {
                RecipesScreen(
                    viewModel = viewModel,
                    navigateToDetails = onNavigateToDetails,
                )
            }
            composable(Routes.FavoriteRecipes.route) {
                FavoriteRecipesScreen(
                    viewModel = viewModel,
                    onNavigateToDetails = onNavigateToDetails,
                )
            }
            composable(Routes.FoodJoke.route) { FoodJokeScreen() }
        }
    }
}

@Composable
private inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(
    navController: NavController,
): T {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel(parentEntry)
}
