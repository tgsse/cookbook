package com.ix.cookbook.data.databases

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ix.cookbook.data.databases.entities.FavoriteRecipeEntity
import com.ix.cookbook.data.databases.entities.RecipesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipesDao {

    // Recipes

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipesEntity: RecipesEntity)

    @Query(value = "SELECT * FROM recipes_table ORDER BY id ASC")
    fun readRecipes(): Flow<List<RecipesEntity>>

    // Favorite Recipes

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteRecipe(favoriteRecipeEntity: FavoriteRecipeEntity)

    @Query(value = "SELECT * FROM favorite_recipes_table ORDER BY id ASC")
    fun readFavoriteRecipes(): Flow<List<FavoriteRecipeEntity>>

    @Delete
    suspend fun deleteFavoriteRecipe(favoriteRecipeEntity: FavoriteRecipeEntity)

    @Query("DELETE FROM favorite_recipes_table where external_id in (:ids)")
    suspend fun deleteFavoriteRecipes(ids: List<Int>)
}
