package com.ix.cookbook.data.databases.recipes

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ix.cookbook.data.databases.recipes.entities.FavoriteRecipeEntity
import com.ix.cookbook.data.databases.recipes.entities.RecipesEntity

@Database(
    entities = [
        RecipesEntity::class,
        FavoriteRecipeEntity::class,
    ],
    version = 1, // NOTE: this needs to be updated if the model changes. Migration is required as well
    exportSchema = false,
)
@TypeConverters(RecipesTypeConverter::class)
abstract class RecipesDatabase : RoomDatabase() {

    abstract fun recipesDao(): RecipesDao
}
