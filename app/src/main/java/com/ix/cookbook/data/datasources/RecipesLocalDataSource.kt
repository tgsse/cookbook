package com.ix.cookbook.data.datasources

import com.ix.cookbook.data.databases.RecipesDao
import com.ix.cookbook.data.databases.entities.FavoriteRecipeEntity
import com.ix.cookbook.data.databases.entities.RecipesEntity
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

//    suspend fun deleteAllFavoriteRecipes() {
//        recipesDao.deleteAllFavoriteRecipes()
//    }
}
