package com.ix.cookbook.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material.icons.outlined.SentimentVerySatisfied
import androidx.compose.ui.graphics.vector.ImageVector
import com.ix.cookbook.R

sealed class Screen(
    val route: String,
    @StringRes val label: Int,
    val icon: ImageVector,
) {
    object Recipes : Screen(
        route = "recipes",
        label = R.string.screen_recipes,
        icon = Icons.Outlined.Restaurant,
    )

    object FavoriteRecipes : Screen(
        route = "favoriteRecipes",
        label = R.string.screen_favorite_recipes,
        icon = Icons.Outlined.Favorite,
    )

    object FoodJoke : Screen(
        route = "foodJoke",
        label = R.string.screen_food_joke,
        icon = Icons.Outlined.SentimentVerySatisfied,
    )
}
