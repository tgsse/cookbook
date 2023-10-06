package com.ix.cookbook.data.datasources.recipes

import com.ix.cookbook.data.databases.recipes.RecipesDao
import com.ix.cookbook.data.databases.recipes.entities.FavoriteRecipeEntity
import com.ix.cookbook.data.databases.recipes.entities.RecipesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecipesLocalDataSource @Inject constructor(
    private val recipesDao: RecipesDao,
) {

    // Recipes

    fun readRecipes(): Flow<List<RecipesEntity>> {
        return recipesDao.readRecipes()
    }

    suspend fun insertRecipes(recipesEntity: RecipesEntity) {
        recipesDao.insertRecipes(recipesEntity)
    }

    // Favorite recipes

    fun readFavoriteRecipes(): Flow<List<FavoriteRecipeEntity>> {
        return recipesDao.readFavoriteRecipes()
    }

    suspend fun insertFavoriteRecipe(favoriteRecipesEntity: FavoriteRecipeEntity) {
        recipesDao.insertFavoriteRecipe(favoriteRecipesEntity)
    }

    suspend fun deleteFavoriteRecipe(favoriteRecipesEntity: FavoriteRecipeEntity) {
        recipesDao.deleteFavoriteRecipe(favoriteRecipesEntity)
    }

    suspend fun deleteFavoriteRecipes(recipes: List<Int>) {
        recipesDao.deleteFavoriteRecipes(recipes)
    }
}
