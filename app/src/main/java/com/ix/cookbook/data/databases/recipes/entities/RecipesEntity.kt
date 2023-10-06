package com.ix.cookbook.data.databases.recipes.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ix.cookbook.data.databases.recipes.RecipesDbUtil.Companion.recipesTable
import com.ix.cookbook.data.models.recipes.Recipes

@Entity(tableName = recipesTable)
class RecipesEntity(
    var recipes: Recipes,
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}
