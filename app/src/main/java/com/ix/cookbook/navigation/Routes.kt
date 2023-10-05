package com.ix.cookbook.navigation

enum class Routes(val route: String) {
    Root("root"),
    Main("main"),

    RecipesList("main/recipesList"),
    FavoriteRecipes("main/favorites"),
    FoodJoke("main/foodJoke"),

    RecipeDetails("recipesDetails"),
}
