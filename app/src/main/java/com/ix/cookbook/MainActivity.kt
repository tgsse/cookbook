package com.ix.cookbook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ix.cookbook.navigation.NavBar
import com.ix.cookbook.navigation.Screen
import com.ix.cookbook.screens.FavoriteRecipesFragment
import com.ix.cookbook.screens.FoodJokeFragment
import com.ix.cookbook.screens.RecipesFragment
import com.ix.cookbook.ui.theme.CookBookTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val navController = rememberNavController()
            CookBookTheme {
                Scaffold(
                    bottomBar = {
                        NavBar(navController)
                    }
                ) { innerPadding ->
                    NavHost(
                        navController,
                        startDestination = Screen.Recipes.route,
                        Modifier.padding(innerPadding)
                    ) {
                        composable(Screen.Recipes.route) { RecipesFragment() }
                        composable(Screen.FavoriteRecipes.route) { FavoriteRecipesFragment() }
                        composable(Screen.FoodJoke.route) { FoodJokeFragment() }
                    }
                }
            }
        }
    }
}
