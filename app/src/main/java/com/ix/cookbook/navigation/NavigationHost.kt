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
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.ix.cookbook.screens.favorites.FavoriteRecipesScreen
import com.ix.cookbook.screens.joke.FoodJokeScreen
import com.ix.cookbook.screens.recipes.RecipesScreen
import com.ix.cookbook.screens.recipes.RecipesViewModel
import com.ix.cookbook.screens.recipes.details.RecipeDetails

private object SubGraphs {
    const val recipes = "recipes"
}

@Composable
fun NavigationHost(
    navController: NavHostController,
) {
    Scaffold(
        bottomBar = {
            NavBar(navController)
        },
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = SubGraphs.recipes,
            Modifier.padding(innerPadding),
        ) {
            recipesGraph(navController)
            composable(Routes.FavoriteRecipes.route) { FavoriteRecipesScreen() }
            composable(Routes.FoodJoke.route) { FoodJokeScreen() }
        }
    }
}

private fun NavGraphBuilder.recipesGraph(navController: NavController) {
    fun navigateToDetails() {
        navController.navigate(
            route = Routes.RecipeDetails.route,
        ) {
            popUpTo(Routes.RecipesList.route)
        }
    }

    navigation(
        startDestination = Routes.RecipesList.route,
        route = SubGraphs.recipes,
    ) {
        composable(Routes.RecipesList.route) { entry ->
            val viewModel = entry.sharedViewModel<RecipesViewModel>(navController = navController)

            RecipesScreen(
                viewModel = viewModel,
                navigateToDetails = { navigateToDetails() },
            )
        }
        composable(Routes.RecipeDetails.route) { entry ->
            val viewModel = entry.sharedViewModel<RecipesViewModel>(navController = navController)
            val state = viewModel.state.collectAsStateWithLifecycle()

            RecipeDetails(
                selectedRecipe = state.value.selectedRecipe,
                onNavigateBack = { navController.popBackStack() },
            )
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
