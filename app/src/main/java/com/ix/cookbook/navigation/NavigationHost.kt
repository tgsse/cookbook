package com.ix.cookbook.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.ix.cookbook.screens.favorites.FavoriteRecipesScreen
import com.ix.cookbook.screens.joke.FoodJokeScreen
import com.ix.cookbook.screens.recipes.RecipesScreen
import com.ix.cookbook.screens.recipes.details.RecipeDetails

@OptIn(ExperimentalMaterial3Api::class)
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
            startDestination = "recipes",
            Modifier.padding(innerPadding),
        ) {
//            composable(Screen.Recipes.route) { RecipesScreen() }
            recipesGraph(navController)
            composable(Screen.FavoriteRecipes.route) { FavoriteRecipesScreen() }
            composable(Screen.FoodJoke.route) { FoodJokeScreen() }
        }
    }
}

fun NavGraphBuilder.recipesGraph(navController: NavController) {
    navigation(startDestination = "list", route = "recipes") {
        composable(Screen.Recipes.route) { RecipesScreen() }
        composable("details") { RecipeDetails() }
    }
}