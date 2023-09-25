package com.ix.cookbook.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ix.cookbook.screens.favorites.FavoriteRecipesScreen
import com.ix.cookbook.screens.joke.FoodJokeScreen
import com.ix.cookbook.screens.recipes.RecipesScreen

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
            startDestination = Screen.Recipes.route,
            Modifier.padding(innerPadding),
        ) {
            composable(Screen.Recipes.route) { RecipesScreen() }
            composable(Screen.FavoriteRecipes.route) { FavoriteRecipesScreen() }
            composable(Screen.FoodJoke.route) { FoodJokeScreen() }
        }
    }
}
